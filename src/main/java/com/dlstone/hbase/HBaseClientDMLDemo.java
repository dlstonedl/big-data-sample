package com.dlstone.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

public class HBaseClientDMLDemo {

    private Connection conn;

    @Before
    public void getConn() throws IOException {
        Configuration config = HBaseConfiguration.create();
        conn = ConnectionFactory.createConnection(config);
    }

    @Test
    public void testPut() throws IOException {
        //获取一个指定表的table对象，进行DML操作
        Table table = conn.getTable(TableName.valueOf("hbase_test"));

        //一个put对应一个rowKey
        Put put = new Put(Bytes.toBytes("001"));
        put.addColumn(Bytes.toBytes("cf_1"), Bytes.toBytes("name"), Bytes.toBytes("bingbing"));
        put.addColumn(Bytes.toBytes("cf_1"), Bytes.toBytes("age"), Bytes.toBytes("18"   ));

        table.put(put);

        table.close();
        conn.close();
    }

    @Test
    public void testDelete() throws IOException {
        Table table = conn.getTable(TableName.valueOf("hbase_test"));

        Delete delete = new Delete(Bytes.toBytes("001"));
        delete.addColumn(Bytes.toBytes("cf_1"), Bytes.toBytes("age"));

        table.delete(delete);

        table.close();
        conn.close();
    }

    @Test
    public void testGet() throws IOException {
        Table table = conn.getTable(TableName.valueOf("hbase_test"));

        Get get = new Get(Bytes.toBytes("001"));

        Result result = table.get(get);

        byte[] value = result.getValue("cf_1".getBytes(), "name".getBytes());
        System.out.println("value = " + new String(value));

        byte[] rowKey = result.getRow();
        System.out.println("rowKey = " + new String(rowKey));

        CellScanner scanner = result.cellScanner();
        while (scanner.advance()) {
            Cell cell = scanner.current();
            byte[] familyArray = cell.getFamilyArray();
            byte[] qualifierArray = cell.getQualifierArray();
            byte[] valueArray = cell.getValueArray();

            System.out.println("familyArray =" + new String(familyArray, cell.getFamilyOffset(), cell.getFamilyLength()));
            System.out.println("qualifierArray = " + new String(qualifierArray, cell.getQualifierOffset(), cell.getQualifierLength()));
            System.out.println("valueArray = " + new String(valueArray, cell.getValueOffset(), cell.getValueLength()));
        }

        table.close();
        conn.close();
    }

    @Test
    public void testScan() throws IOException {
        Table table = conn.getTable(TableName.valueOf("hbase_test"));

        Scan scan = new Scan();
        //默认包含起始行键
        scan.withStartRow("001".getBytes());
        //默认不包含结束行键，范围查询：结束键可拼接不可见字节(\000)，就可包含结束行键，如：1000\000
        scan.withStopRow("002".getBytes());
        ResultScanner scanner = table.getScanner(scan);

        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            Result result = iterator.next();
            byte[] rowKey = result.getRow();
            System.out.println("rowKey = " + new String(rowKey));
        }

        table.close();
        conn.close();
    }
}
