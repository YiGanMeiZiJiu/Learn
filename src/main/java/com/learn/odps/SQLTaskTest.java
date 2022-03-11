//package com.learn.odps;
//
//import com.aliyun.odps.Instance;
//import com.aliyun.odps.Odps;
//import com.aliyun.odps.OdpsException;
//import com.aliyun.odps.TableSchema;
//import com.aliyun.odps.data.Record;
//import com.aliyun.odps.data.RecordReader;
//import com.aliyun.odps.task.SQLTask;
//import com.aliyun.odps.tunnel.TableTunnel;
//import com.aliyun.odps.tunnel.TunnelException;
//
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * @author 王🍊 2020-01-19
// */
//public class SQLTaskTest {
//
//    private static final String accessKey = "userAccessId";
//    private static final String endPoint = "http://service.cn-shanghai.maxcompute.aliyun.com/api";
//    private static final String project = "userProject";
//    private static final String sql = "userSQL";
//    private static final String table = "Tmp_" + UUID.randomUUID().toString().replace("-", "_");//用随机字符串作为临时表的名字。
//
//    /**
//     * 获取odps对象
//     */
//    private static final Odps odps = MaxCompute.getOdps();
//
//    /**
//     * 运行sql，将结果保存至临时表
//     * 临时表存在时间1天
//     */
//    private static void runSql() {
//        Instance instance;
//        StringBuilder sb = new StringBuilder("Create Table ").append(table).append(" lifecycle 1 as ").append(sql);
//        try {
//            System.out.println(sb.toString());
//            instance = SQLTask.run(odps, sb.toString());
//            // 等待结果执行成功
//            instance.waitForSuccess();
//        } catch (OdpsException e) {
//            // log error
//            System.out.println("查询结果导出失败");
//        }
//    }
//
//    /**
//     * 查询临时保存结果
//     */
//    private static void tunnel() {
//        TableTunnel tunnel = new TableTunnel(odps);
//        try {
//            TableTunnel.DownloadSession downloadSession = tunnel.createDownloadSession(project, table);
//            System.out.println("Session Status is : " + downloadSession.getStatus().toString());
//            long count = downloadSession.getRecordCount();
//            System.out.println("RecordCount is: " + count);
//            RecordReader recordReader = downloadSession.openRecordReader(0, count);
//            Record record;
//            while ((record = recordReader.read()) != null) {
//                consumeRecord(record, downloadSession.getSchema());
//            }
//            recordReader.close();
//        } catch (TunnelException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 打印结果
//     * @param record
//     * @param tableSchema
//     */
//    private static void consumeRecord(Record record, TableSchema tableSchema) {
//        System.out.println(record.getString("username") + "," + record.getBigint("cnt"));
//    }
//}
