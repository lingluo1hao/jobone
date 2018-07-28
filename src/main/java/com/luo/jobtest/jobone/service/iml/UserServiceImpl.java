package com.luo.jobtest.jobone.service.iml;

import com.luo.jobtest.jobone.entity.User;
import com.luo.jobtest.jobone.repository.UserRepository;
import com.luo.jobtest.jobone.service.UserService;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@CommonsLog
@Service
public class UserServiceImpl implements UserService {
    private  final static Log log=LogFactory.getLog(UserServiceImpl.class);
    @Resource
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> findById(String id){
        return  userRepository.findById(id);
    }

    @Override
    public List<User> findAll(){
        return  userRepository.findAll();
    }

    @Override
    public  User save(User user){
        return  userRepository.save(user);
    }

    @Override
    public void delete(String id){
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public  void batchInsert(List<User> list){
        try{
            for(int i = 0; i < list.size(); i++){
                em.persist(list.get(i));
                if(i % 100==0){
                    em.flush();
                    em.clear();
                }
            }
            log.info("save to DB success,list is {}"+list.toString());
        }catch (Exception e){
            log.error("batch insert data failuer.");
            e.printStackTrace();
        }
    }
}
