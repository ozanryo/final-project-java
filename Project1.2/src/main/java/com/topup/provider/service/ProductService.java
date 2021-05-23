package com.topup.provider.service;

import com.google.gson.Gson;
import com.topup.provider.model.Provider;
import com.topup.provider.response.send;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ProductService {
    private SqlSession session;
    private send messageToDB = new send();

    public void connectMyBatis() throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
    }

    public void findAllProduct() throws IOException, TimeoutException {
        System.out.println("Memulai proses pengambilan data provider");
        connectMyBatis();

        List<Provider> provider = session.selectList("topupPulsa.getAll");

        messageToDB.sendProviderListtoDB(provider);
        System.out.println("Berhasil mengirim database list produk");

        session.commit();
        session.close();

    }

    public void findProductPriceToDB(String message) throws IOException, TimeoutException {
        System.out.println("Memulai proses pengambilan harga produk");
        connectMyBatis();

        int code = Integer.parseInt(message);

        Provider provider  = (Provider) session.selectOne("topupPulsa.getCode", code);

        Double price = provider.getHarga();

        messageToDB.sendRequestCodePriceToDB(String.valueOf(price));

        System.out.println("Berhasil mengirim database list produk");

        session.commit();
        session.close();

    }

    public Provider insertProductFromDB(String message) throws IOException, TimeoutException {
        System.out.println("Memulai proses pengiriman data provider");
        connectMyBatis();

        System.out.println("Berhasil mengirim database list produk");

        Provider provider = new Gson().fromJson(message, Provider.class);

        session.commit();
        session.close();

        return provider;

    }

    public void countProductDB(String message) throws IOException, TimeoutException {
        System.out.println("Memulai proses penambahan produk terjual pada database provider");
        connectMyBatis();

        Provider provider = session.selectOne("topupPulsa.getCodeFromProviderDB", message);

        provider.tambahTerjual();

        int hasil = session.update("topupPulsa.updateCount", provider);
        session.commit();

        if (hasil==1){
            System.out.println("Data sudah di update");
        } else {
            System.out.println("Data gagal di update");
        }


    }


}
