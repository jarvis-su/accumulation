package jarvis.utils.mail;

public class SendEmail {
	public SendEmail() {
	}

	/*** 以后需要两个参数：接收方地址 、 内容 ***/
	public static void send(String subject, String toaddress, String content)
			throws Exception {

		String hostName = ReadPropertity.getProperty("emailsmtp");
		String fromAddress = ReadPropertity.getProperty("emailaddress");
		String fromAPass = ReadPropertity.getProperty("emailpass");

		EmailHandle emailHandle = new EmailHandle(hostName);
		emailHandle.setFrom(fromAddress);
		emailHandle.setNeedAuth(true);
		emailHandle.setSubject(subject);
		emailHandle.setBody(content);
		emailHandle.setTo(toaddress);
		emailHandle.setFrom(fromAddress);
		emailHandle.addFileAffix("E:/img.jpg");// 附件文件路径
		emailHandle.setNamePass(fromAddress, fromAPass);
		emailHandle.sendEmail();
	}

	public static void main(String[] args) {
		try {
			SendEmail.send("带附件的邮件测试", "mail2sujie@gmail.com", "测试内容");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}