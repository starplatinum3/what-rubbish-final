//package com.example.whatrubbish.crawler;
//
//import java.io.IOException;
//
//public class GetDataThd extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            try {
//                Document doc = Jsoup.connect(BLOG_URL).get();//通过url获取到网页内容
//
//                Elements elements = doc.getElementsByClass("link_title");//查找所有class为"link_title"的元素
//                for (Element e : elements) {
//                    Elements titles = e.getElementsByTag("a");//在每一个找到的元素中，查找<a>标签
//                    for (Element title : titles) {
//                        //将找到的标签数据封装起来
//                        TitleBean bean = new TitleBean();
//                        bean.setTitle(title.text());//获取标签的内容，也就是文章标题
//                        bean.setUrl("http://blog.csdn.net" + title.attr("href"));//获取标签属性，也就是文章链接
//                        list.add(bean);
//                    }
//                }
//                msgHandler.sendEmptyMessage(MSG_LOAD_OK);//通知UI更新List
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }