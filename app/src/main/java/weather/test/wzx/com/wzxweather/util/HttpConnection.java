package weather.test.wzx.com.wzxweather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import weather.test.wzx.com.wzxweather.interfaces.HttpCallBackListener;

/**
 * Created by 王钟鑫 on 17/4/17.
 */

public class HttpConnection {

    public static void httpConnHelp(final String address, final HttpCallBackListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {

                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    //connection.setDoOutput(true);//dooutput是允许向服务器发送数据,在获取.json文件的时候会出错,产生405错误
                    //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                    int code = connection.getResponseCode();
                    if (code == 200) {


                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                        StringBuilder builder = new StringBuilder();
                        String str = "";
                        while ((str = reader.readLine()) != null) {


                            builder.append(str);

                        }

                        if (listener != null) {


                            listener.onSuccess(builder);

                        }

                    } else {


                        LogUtil.e("HttpConnectin", "连接错误:code=" + code);

                    }
                } catch (MalformedURLException e) {

                    e.printStackTrace();


                } catch (IOException e) {

                    e.printStackTrace();
                } catch (Exception e) {

                    if (listener != null) {


                        listener.onFailour(e);

                    }

                }finally
                    {


                    if (connection != null) {


                        connection.disconnect();
                        connection = null;
                    }

                }

            }
        }).start();


    }
}
