package com.kevin.util;


import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2019-03-07
 * Time: 10:44
 */
public class HttpUtils {
    public final static CloseableHttpAsyncClient client;
    public static RequestConfig requestConfig;
    public static List<Integer> datas = new ArrayList<>();
    public static long timeGap = 60*1000L; //每次http请求获取数据的最小时间区间为60s
    public static long MAX_TIME_GAP = 24*60*60*1000L;//每次用户请求数量限制
    public static String getSampleDataProfileUrl = "https://zciwpugh35.execute-api.ap-south-1.amazonaws.com/test/eventList";
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    static{
         requestConfig = RequestConfig.custom()
                .setConnectTimeout(50000)
                .setSocketTimeout(50000)
                .setConnectionRequestTimeout(50000)
                .build();

        //配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().
                setIoThreadCount(1000)//(io密集型)
                .setSoKeepAlive(true)
                .build();
        //设置连接池大小
        ConnectingIOReactor ioReactor=null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connManager.setMaxTotal(50);
        connManager.setDefaultMaxPerRoute(50);
        client = HttpAsyncClients.custom().
                setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        client.start();

    }


    public static void initAll(){
        datas = new ArrayList<>();
    }

    /**
     * 请求vcloud接口下载数据，每次60s，每个请求完成时
     * 任务计数器counter+1，检测当前所有（taskNumber个）任务是否完成，若是则通过condition通知主线程
     * @param uri vcloud数据请求接口
     * @param condition 线程同步对象监视器，主线程通过condition.wait()等待所有子任务完成
     * @param res  请求结果存放至此
     * @param counter 任务计数器，所有任务完成时通过condition唤醒主线程，告知所有下载任务已完成
     * @param taskNumber 任务总数
     */
    public static void httpAsc(URI uri,Object condition,List res,AtomicInteger counter,int taskNumber){
        //创建httpGet
        HttpGet httpGet=new HttpGet(uri);
        Back back = new Back(condition,res,counter,taskNumber);
        client.execute(httpGet,back);
    }

    /**
     *
     * @param sensorId
     * @param start 00:00:00
     * @return
     */
    public static List getDataTask(String sensorId, long start,long end){
        //if(end-start>MAX_TIME_GAP) throw new BaseException(400,"时间区间不能超过"+MAX_TIME_GAP);
        if(end-start<=timeGap){
            end = start+timeGap;
        }
        //多个http任务将结果写入此集合，使用同步集合
        List res = new CopyOnWriteArrayList<>();
        //任务同步监视器
        Object condition = new Object();
        //统计任务完成数
        AtomicInteger counter = new AtomicInteger(0);
        //本次task任务总数
        int taskNumber = (int)((end-start)/timeGap);
        if((end-start)%timeGap>0){
            taskNumber++;
        }
        long begin = System.currentTimeMillis();
        for(int i=0;i<taskNumber;i++){
           try {
               long curSatrt = start+i*timeGap;
               //请求参数start和end为闭区间，-1 防止重复
               long curEnd = curSatrt+timeGap-1;
               //防止最后一个区间超过限制
               if(curEnd>end){
                   curEnd = end;
               }
               logger.info("satrt:{},end:{}",curSatrt,curEnd);
               //每个http请求60s的数据量
               URI uri = new URIBuilder(getSampleDataProfileUrl).setParameter("start", ""+curSatrt)
                       .setParameter("end", ""+curEnd)
                       .setParameter("appId", "Cardiac Scout")
                       .setParameter("sensorid",sensorId).build();
               httpAsc(uri, condition, res,counter,taskNumber);
           }catch (Exception e){
               e.printStackTrace();
           }
        }
        //等待Task完成,计数器达到任务数量后唤醒condition
        synchronized (condition) {
            try {
                //若60s内未下载完提前结束下载，返回数据，此时可能导致部分数据缺失，一般限制半小时的下载数据量，若
                //要提高单次数据下载量，需要增加等待时间
                condition.wait(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end1 = System.currentTimeMillis();
        logger.info("本次task成功获取数据{}，共耗时{}",res.size(),(end1-begin));
        return res;
    }


    public static void main(String[] args)throws Exception {
        //long start = 1561548252000L;
        // start = 1552008208071L;
        //String sensorId = "ECGRec_201845/C600010";
        String sensorId = "ECGRec_330R06G";
        sensorId = "201848/C600094";
        long start = 1562322768991L;
        long end   = start+6*60*60*1000L;


        //FileUtils.writeToFile("201848_C600094.txt",JSON.toJSONString(list));

    }


    static class Back implements FutureCallback<HttpResponse> {
        private List result;
        private Object condition;
        private AtomicInteger counter;
        private int taskNumber;

        private long start = System.currentTimeMillis();
        public Back(){
        }
        public Back(Object condition,List result,AtomicInteger counter,int taskNumber){

            this.condition = condition;
            this.result = result;
            this.counter = counter;
            this.taskNumber = taskNumber;
        }

        public void completed(HttpResponse httpResponse) {
            try {
                String msg = EntityUtils.toString(httpResponse.getEntity());

                //每完成一个work，计数器+1
                counter.incrementAndGet();
                //若所有task完成，唤醒主线程
                if(counter.get()>=taskNumber){
                    synchronized (condition) {
                        condition.notify();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void failed(Exception e) {
            e.printStackTrace();
            System.err.println(" cost is:"+(System.currentTimeMillis()-start)+":"+e.getMessage());
            counter.incrementAndGet();
            if(counter.get()>=taskNumber){
                synchronized (condition) {
                    condition.notify();
                }
            }
        }

        public void cancelled() {

        }
    }

}
