package ch.demo.web;

import java.io.IOException;

import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

@MessageDriven(mappedName = "MyQueue")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudentRegistrationService implements MessageListener {

  @Inject // Let's ignore this for the moment
  private transient Logger logger;

  @PersistenceContext
  EntityManager em;

  @Resource // Let's ignore this for the moment
  private MessageDrivenContext mdbContext;

  public void onMessage(Message inMessage) {
      TextMessage msg = null;
      try {

          msg = (TextMessage) inMessage;
          logger.info("MESSAGE BEAN: Message received: " + msg.getText());
          logger.info(Thread.currentThread().getName());

      } catch (JMSException e) {
          em.getTransaction().rollback();
      }
  }
}

