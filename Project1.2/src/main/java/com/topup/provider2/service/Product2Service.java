package com.topup.provider2.service;

import com.google.gson.Gson;
import com.topup.provider.model.Provider;
import com.topup.provider2.model.Provider2;
import com.topup.provider2.response.send;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Product2Service {
    private SqlSession session;
    private send messageToDB = new send();

    public void connectMyBatis() throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
    }

    public void findAllProduct2() throws IOException, TimeoutException {
        System.out.println("Memulai proses pengambilan data provider");
        connectMyBatis();

        List<Provider2> provider = session.selectList("topupPulsa.getAll2");

        messageToDB.sendProvider2ListtoDB(provider);
        System.out.println("Berhasil mengirim database list produk");

        session.commit();
        session.close();

    }

    public void findProduct2PriceToDB(String message) throws IOException, TimeoutException {
        System.out.println("Memulai proses pengambilan harga produk");
        connectMyBatis();

        int code = Integer.parseInt(message);

        Provider2 provider  = (Provider2) session.selectOne("topupPulsa.getCode2", code);

        Double price = provider.getHarga();

        messageToDB.sendRequestCodePrice2ToDB(String.valueOf(price));

        System.out.println("Berhasil mengirim database list produk");

        session.commit();
        session.close();

    }

    public Provider2 insertProductFromDB(String message) throws IOException, TimeoutException {
        System.out.println("Memulai proses pengiriman data provider");
        connectMyBatis();

        System.out.println("Berhasil mengirim database list produk");

        Provider2 provider = new Gson().fromJson(message, Provider2.class);

        session.commit();
        session.close();

        return provider;
    }

    public void countProduct2DB(String message) throws IOException, TimeoutException {
        System.out.println("Memulai proses penambahan produk terjual pada database provider");
        connectMyBatis();

        Provider2 provider = session.selectOne("topupPulsa.getCodeFromProvider2DB", message);

        provider.tambahTerjual();

        int hasil = session.update("topupPulsa.updateCount2", provider);
        session.commit();

        if (hasil==1){
            System.out.println("Data sudah di update");
        } else {
            System.out.println("Data gagal di update");
        }


    }
}
