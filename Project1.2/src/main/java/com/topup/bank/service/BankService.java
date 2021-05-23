package com.topup.bank.service;

import com.topup.bank.model.Bank;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeoutException;

public class BankService {
    private SqlSession session;

    public void connectMyBatis() throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
    }

    public void insertBank(String transfercode) throws IOException, TimeoutException {
        connectMyBatis();
        Bank bank = new Bank();
        bank.setTransferCode(transfercode);
        bank.getStatus();

        System.out.println("Memulai proses input data transaksi pada database bank");

        session.insert("topupPulsa.inputBank", bank);

        System.out.println("Proses Input Data Transaksi Selesai");

        session.commit();
        session.close();
    }


}
