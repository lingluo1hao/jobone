package com.luo.jobtest.jobone.service.iml;

import com.luo.jobtest.jobone.entity.Email;
import com.luo.jobtest.jobone.service.EmailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private  final static Log log=LogFactory.getLog(EmailServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public  void batchInsert(List<Email> listMail){{
        try{
            for(int i = 0; i < listMail.size(); i++){
                em.persist(listMail.get(i));
                if(i % 100==0){
                    em.flush();
                    em.clear();
                }
            }
            log.info("save to DB success,list is {}"+listMail.toString());
        }catch (Exception e){
            log.error("batch insert data failuer."+ e.getMessage());
            e.printStackTrace();
        }
    }

    };
}
