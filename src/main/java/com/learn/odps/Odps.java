//package com.learn.odps;
//
//import com.aliyun.odps.account.Account;
//import com.aliyun.odps.account.AliyunAccount;
//
///**
// * @author 王🍊 2020-01-17
// */
//public class Odps {
//
//    private static volatile Odps odps;
//
//    /**
//     * 私有构造方法
//     */
//    private Odps () {}
//
//    /**
//     * 单例获得Odps对象
//     * @return
//     */
//    private static Odps getOdps() {
//        if (odps == null) {
//            synchronized (Odps.class) {
//                if (odps == null) {
//                    Account account = new AliyunAccount("access_id", "access_key");
//                    com.aliyun.odps.Odps odps = new com.aliyun.odps.Odps(account);
//                    String odpsUrl = "odps endpoint";
//                    odps.setEndpoint(odpsUrl);
//                    // 类似database；
//                    odps.setDefaultProject("my project");
//                }
//            }
//        }
//        return odps;
//    }
//
//}
