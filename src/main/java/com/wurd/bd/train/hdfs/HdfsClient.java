package com.wurd.bd.train.hdfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.annotation.Cacheable;

public class HdfsClient {

    FileSystem fs = null;

    @Before
    public void init() throws Exception {
        // 1 获取文件系统
        Configuration conf = new Configuration();
        //设立的设置url请注意，设置core-site.xml中配置fs.defaultFS的地址
        fs = FileSystem.get(new URI("hdfs://192.168.85.111:9820"), conf, "root");

    }

    /**
     * 从本地上传文件到hdfs
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @Test
    public void testAdd()  throws IOException, URISyntaxException, InterruptedException {
        // 第一个参数：本地上传文件路径
        // 第二个参数：上传到hdfs的路径
        fs.copyFromLocalFile(new Path("C:\\Users\\len\\Desktop\\test.txt"),
                new Path("/test/test1/test.log"));
        // 关闭资源
        fs.close();
    }

    /**
     * 从hdfs中复制文件到本地文件系统 --- 下载
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @Test
    @Cacheable
    public void testDownloadFileToLocal() throws IllegalArgumentException, IOException {
        // 执行下载操作
        // boolean delSrc 指是否将原文件删除
        // Path src 指要下载的文件路径
        // Path dst 指将文件下载到的路径
        // boolean useRawLocalFileSystem 是否开启文件校验
        fs.copyToLocalFile(false, new Path("/test/test.txt"),
                new Path("C:\\Users\\len\\Desktop"), false);
        FileStatus a = fs.getFileStatus(new Path("/"));
        fs.close();
    }

    /**
     * 文件更名
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    @Test
    public void testRename() throws IOException, InterruptedException, URISyntaxException {
        // 1 修改文件名称
        fs.rename(new Path("/test/test1"), new Path("/test/test1.txt"));
        // 2 关闭资源
        fs.close();
    }

    @Test
    public void testDelete() throws IOException, InterruptedException, URISyntaxException{
        // 1 执行删除
        fs.delete(new Path("/test/test1.txt"), true);
        // 2 关闭资源
        fs.close();
    }

    //查看目录信息，只显示文件
    @Test
    public void testListFiles() throws FileNotFoundException, IllegalArgumentException, IOException {
        // 获取文件详情
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println("========" + fileStatus.getPath() + "=========");
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());

            // 获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation bl : blockLocations) {
                System.out.println("block-length:" + bl.getLength() + "--" + "block-offset:" + bl.getOffset());
                String[] hosts = bl.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
            System.out.println("--------------打印的分割线--------------");
        }
        //  关闭资源
        fs.close();
    }

    /**
     * 查看文件及文件夹信息
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws FileNotFoundException
     */
    @Test
    public void testListAll() throws FileNotFoundException, IllegalArgumentException, IOException {
        // 判断是文件还是文件夹
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        String flag = "";
        for (FileStatus fstatus : listStatus) {
            // 如果是文件
            if (fstatus.isFile()) {
                flag = "file-- ";
            } else {
                flag = "directory-- ";
            }
            System.out.println(flag + fstatus.getPath().getName());
            System.out.println(fstatus.getPermission());
        }
    }

}
