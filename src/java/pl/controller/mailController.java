///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pl.controller;
//import java.io.Serializable;
//import java.util.Properties;
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.faces.bean.SessionScoped;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
///**
// *
// * @author Maciej
// */
//@SessionScoped
//
//public class mailController implements Serializable{
//    
//   public static void main(String[] args) {
//
//		final String username = "iprl.mail.service@gmail.com";
//		final String password = "Maciek1995";
//                
//
//		Properties props = new Properties();
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", "587");
//
//		Session session = Session.getInstance(props,
//		  new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
//			}
//		  });
//
//		try {
//
//			
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress("mateusz.ciepielewski@gmail.com"));
//			message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse("ciepielewski.maciej@gmail.com"));
//			message.setSubject("Internetowa platmorma rozgrywek ligowych");
//			message.setText("Cześć");
//
//			Transport.send(message);
//
//			System.out.println("Done");
//
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
//	}
//}
