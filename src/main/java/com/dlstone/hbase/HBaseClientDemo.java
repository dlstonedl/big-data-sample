package com.dlstone.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.junit.Test;

import java.io.IOException;

public class HBaseClientDemo {

    /**
     * hbase-client中包含hadoop-auth，注意与hadoop-client版本的匹配
     */
    @Test
    public void testCreateTable() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost:2181");
        Connection conn = ConnectionFactory.createConnection(config);

        //从conn中构造一个DDL操作器
        Admin admin = conn.getAdmin();

        TableDescriptor tableDescriptor = TableDescriptorBuilder
                .newBuilder(TableName.valueOf("hbase_test"))
                .setColumnFamily(ColumnFamilyDescriptorBuilder.of("cf_1"))
                .build();

        admin.createTable(tableDescriptor);

        admin.close();
        conn.close();
    }

    @Test
    public void testDropTable() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost:2181");
        Connection conn = ConnectionFactory.createConnection(config);

        Admin admin = conn.getAdmin();

        admin.disableTable(TableName.valueOf("hbase_test"));
        admin.deleteTable(TableName.valueOf("hbase_test"));

        admin.close();
        conn.close();
    }

    @Test
    public void testAlterTable() throws IOException {
        Configuration config = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(config);

        Admin admin = conn.getAdmin();

        TableDescriptor tableDescriptor = TableDescriptorBuilder
                .newBuilder(TableName.valueOf("hbase_test"))
                .setColumnFamily(ColumnFamilyDescriptorBuilder.of("cf_1"))
                .setColumnFamily(ColumnFamilyDescriptorBuilder.of("cf_2"))
                .build();

        admin.modifyTable(tableDescriptor);



    }
}
