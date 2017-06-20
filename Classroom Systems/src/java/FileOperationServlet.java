import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;//01140360360


public class FileOperationServlet extends HttpServlet {


	
	
	//Connection connection=null;

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			System.out.println(" servlet call : file operation  ");
			System.out.println(" id :"+request.getParameter("id"));
			
			String server = "www.myserver.com";
			int port = 21;
			String user = "user";
			String pass = "pass";

			FTPClient ftpClient = new FTPClient();
			try {

				ftpClient.connect(server, port);
				ftpClient.login(user, pass);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				
				int choice=Integer.parseInt(request.getParameter("id"));
				  switch (choice){
		                 case  1:
		                	 UploadFile(ftpClient);
		            	 break;
		                 case  2:
		                	 makeDirectories(ftpClient, "dirPath");
			            	 break;
		                 case  3:
		                	 deleteFile(ftpClient);
			            	 break;
		                 case  4:
		                	 CheckIfDirectoryExists(ftpClient,"dirpath");
			            	 break;
		                 case  5:
		                	 downloadFile(ftpClient);
			            	 break;
		                 case  6:
		                	 ListFiles(ftpClient);
			            	 break;	 
		                 case 7:
		                	 removeDirectory(ftpClient);
		                 break;	
		                 
				  }
				
			} catch (IOException ex) {
				System.out.println("Error: " + ex.getMessage());
				ex.printStackTrace();
			} finally {
				try {
					if (ftpClient.isConnected()) {
						ftpClient.logout();
						ftpClient.disconnect();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
		}
			
		
		public void UploadFile(FTPClient ftpClient){
			File firstLocalFile = new File("D:/Test/Projects.zip");

			String firstRemoteFile = "Projects.zip";
			
			InputStream inputStream;
			try {
				inputStream = new FileInputStream(firstLocalFile);
				System.out.println("Start uploading first file");
				boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
				inputStream.close();
				if (done) {
					System.out.println("The first file is uploaded successfully.");
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
		
	public boolean	makeDirectories(FTPClient ftpClient, String dirPath){
		
			try{
	        String[] pathElements = dirPath.split("/");
	        if (pathElements != null && pathElements.length > 0) {
	            for (String singleDir : pathElements) {
	                boolean existed = ftpClient.changeWorkingDirectory(singleDir);
	                if (!existed) {
	                    boolean created = ftpClient.makeDirectory(singleDir);
	                    if (created) {
	                        System.out.println("CREATED directory: " + singleDir);
	                        ftpClient.changeWorkingDirectory(singleDir);
	                    } else {
	                        System.out.println("COULD NOT create directory: " + singleDir);
	                        return false;
	                    }
	                }
	            }
	        }
	        return true;
			}catch(IOException e){
				System.out.println(" io Exception");
				e.printStackTrace();
				return false;
			}
	    }

		public boolean deleteFile(FTPClient ftpClient){
			
			String fileToDelete = "/repository/video/cool.mp4";
			 
	        boolean deleted;
			try {
				deleted = ftpClient.deleteFile(fileToDelete);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return false;
		}
		
		public void CheckIfDirectoryExists(FTPClient ftpClient,String dirPath){
			try {
				boolean exist=checkDirectoryExists(ftpClient,dirPath);
				exist = checkFileExists(ftpClient,dirPath);
				System.out.println("Is file " + dirPath + " exists? " + exist);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
		boolean checkFileExists(FTPClient ftpClient,String filePath) throws IOException {
			int returnCode=0;
			InputStream inputStream = ftpClient.retrieveFileStream(filePath);
			returnCode = ftpClient.getReplyCode();
			if (inputStream == null || returnCode == 550) {
				return false;
			}
			return true;
		}
		boolean checkDirectoryExists(FTPClient ftpClient,String dirPath) throws IOException {
			int returnCode=0;
			ftpClient.changeWorkingDirectory(dirPath);
			returnCode = ftpClient.getReplyCode();
			if (returnCode == 550) {
				return false;
			}
			return true;
		}
		
		public boolean downloadFile(FTPClient ftpClient){
			String remoteFile1 = "/test/video.mp4";
			File downloadFile1 = new File("D:/Downloads/video.mp4");
			OutputStream outputStream1;
			boolean success;
			try {
				outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
				success = ftpClient.retrieveFile(remoteFile1, outputStream1);
				outputStream1.close();
				return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;

			
		}
		
		public void ListFiles(FTPClient ftpClient){
			
			String dirToList = "/public_html/images/articles";
			try {
				listDirectory(ftpClient, dirToList, "", 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void listDirectory(FTPClient ftpClient, String parentDir,
				String currentDir, int level) throws IOException {
			String dirToList = parentDir;
			if (!currentDir.equals("")) {
				dirToList += "/" + currentDir;
			}
			FTPFile[] subFiles = ftpClient.listFiles(dirToList);
			if (subFiles != null && subFiles.length > 0) {
				for (FTPFile aFile : subFiles) {
					String currentFileName = aFile.getName();
					if (currentFileName.equals(".") || currentFileName.equals("..")) {
						// skip parent directory and directory itself
						continue;
					}
					for (int i = 0; i < level; i++) {
						System.out.print("\t");
					}
					if (aFile.isDirectory()) {
						System.out.println("[" + currentFileName + "]");
						listDirectory(ftpClient, dirToList, currentFileName,
								level + 1);
					} else {
						System.out.println(currentFileName);
					}
				}
			}
		}
		
		public boolean removeDirectory(FTPClient ftpClient){

			String dirToRemove = "/public/video/temp";

			boolean deleted;
			try {
				deleted = ftpClient.removeDirectory(dirToRemove);
				return deleted;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
}
