package douyu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HttpUtil;
import util.UuidUtil;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class ImgMM {

    public static void getImg(String url) throws IOException, InterruptedException {
        final BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<String>(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        Document doc = Jsoup.connect(url).get();
        final Elements elementsByClass = doc.getElementsByClass("imgbox");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Element byClass : elementsByClass) {
                    Elements select = byClass.select("img[data-original]");
                    String attr = select.attr("data-original");
                    try {
                        if(!attr.contains("default")){
                            blockingQueue.put(attr);
                            System.out.println("入列----->"+attr);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        for(int i=0;i<5;i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            String url = blockingQueue.take();
                            System.out.println("开始下载--->"+url);
                            HttpUtil.download(url,"E:\\img"+"/"+ UuidUtil.getUuid()+".jpg");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 1; i < 6; i++) {
            getImg("https://www.douyu.com/directory/game/yz?page="+i);
        }
    }
}
