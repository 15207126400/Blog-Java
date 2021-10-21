package com.ivan.blog.utils;

import com.jcraft.jsch.*;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

/*
 *  @Author: Ivan
 *  @Description:   解释一下SFTP的整个调用过程，这个过程就是通过Ip、Port、Username、Password获取一个Session,
 *                  然后通过Session打开SFTP通道（获得SFTP Channel对象）,再在建立通道（Channel）连接，最后我们就是
 *                  通过这个Channel对象来调用SFTP的各种操作方法.总是要记得，我们操作完SFTP需要手动断开Channel连接与Session连接。
 *  @Date: 2019/11/29 17:01
 */
public class SFtpUtil {
	
    private ChannelSftp channel;
    private Session session;

	/*
	 使用端口号、用户名、密码以连接SFTP服务器
	 */
    public SFtpUtil() {
    	 this.connectServer("127.0.0.1", 22, "root", "123456");
    }
    
    public SFtpUtil(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {
    	  this.connectServer(ftpHost, ftpPort, ftpUserName, ftpPassword);
    }


    public void connectServer(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {
        try {
            // 创建JSch对象
            JSch jsch = new JSch();
            // 根据用户名，主机ip，端口获取一个Session对象
            session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
            if (ftpPassword != null) {
                // 设置密码
                session.setPassword(ftpPassword);
            }
            Properties configTemp = new Properties();
            configTemp.put("StrictHostKeyChecking", "no");
            // 为Session对象设置properties
            session.setConfig(configTemp);
            // 设置timeout时间
            session.setTimeout(60000);
            session.connect();
            // 通过Session建立链接
            // 打开SFTP通道
            channel = (ChannelSftp) session.openChannel("sftp");
            // 建立SFTP通道的连接
            channel.connect();
            
        } catch (JSchException e) {
            //throw new RuntimeException(e);
        }
    }

    /**
     * 断开SFTP Channel、Session连接
     */
    public void closeChannel() {
        try {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        } catch (Exception e) {
            //
        }
    }

    /**
     * 判断指定路径是否存在
     * @param path  
     * @return
     */
    public boolean isExistDir(String path){
        boolean isExist = false;
        try {
            SftpATTRS sftpATTRS = channel.lstat(path);
            isExist = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isExist = false;
            }
        }
        return isExist;
    }

    /**
     * 创建一个文件目录
     */
    public void createDir(String createpath, ChannelSftp sftp) {
        try {
            if (isExistDir(createpath)) {
                channel.cd(createpath);
                return;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isExistDir(filePath.toString())) {
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }
            }
            channel.cd(createpath);
        } catch (SftpException e) {

        }
    }

    /**
     * 上传文件	(网络流传输)
     *
     * @param input      本地文件
     * @param remoteFile 远程文件
     */
    public void upload(BufferedInputStream input, String remoteFile, String imagePath) {
        try {
            createDir("/root/ftp/files/" + imagePath, channel);
            channel.put(input, remoteFile);
            channel.quit();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 上传文件	(普通流传输)
     *
     * @param input      本地文件
     * @param remoteFile 远程文件
     */
    public void upload(InputStream input, String remoteFile) {
        try {
        	channel.cd("/root/ftp/files/");
            channel.put(input, remoteFile);
            channel.quit();
        } catch (SftpException e) {
            //e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFile 远程文件
     * @param localFile  本地文件
     */
    public void download(String remoteFile, String localFile) {
        try {
            channel.get(remoteFile, localFile);
            channel.quit();
        } catch (SftpException e) {
            //e.printStackTrace();
        }
    }

}