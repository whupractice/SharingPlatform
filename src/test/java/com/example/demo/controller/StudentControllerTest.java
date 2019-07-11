package com.example.demo.controller;

import com.example.demo.entity.SLEntity;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    StudentService studentService;

    @Test
    public void getUserFromToken() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<SLEntity> slEntities = new ArrayList<>();
        SLEntity slEntity = new SLEntity();
        slEntity.setPhone(123);
        slEntity.setLessonId(456);
        slEntities.add(slEntity);

        //UserDetails userDetails = createUser()

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String id = (String) args[0];
            Assert.assertEquals(id,"123");
            bitSet.set(0, true);
            return slEntities;
        }).when(studentService).getUserFromToken(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getUserFromToken")
                .param("token","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void login() {
    }

    @Test
    public void logout() {
    }

    @Test
    public void getNickNameByPhone() {
    }

    @Test
    public void register() {
    }

    @Test
    public void registerLessonManager() {
    }

    @Test
    public void registerManager() {
    }

    @Test
    public void getStuById() {
    }

    @Test
    public void getStuByNickName() {
    }

    @Test
    public void getStuBySchoolName() {
    }

    @Test
    public void getAllStudent() {
    }

    @Test
    public void getAllManager() {
    }

    @Test
    public void updateStudent() {
    }

    @Test
    public void updateLessonManager() {
    }

    @Test
    public void updateManager() {
    }

    @Test
    public void deleteLesson() {
    }

    @Test
    public void getLessonManagerPages() {
    }

    @Test
    public void getLessonManagerPagesByName() {
    }

    @Test
    public void getLessonManagerPagesBySchool() {
    }

    @Test
    public void getSkillImg() {
    }

    @Test
    public void getAllGraph() {
    }




    private UserDetails createUser(String userName, String password, String[] roles){
        return new UserDetails() {

            //private static final long serialVersionUID = 6905138725952656074L;

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<>();

//                //这是增加了一种名为query的权限，可以使用 @hasAuthority("query") 来判断
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("query");
//                authorities.add(authority);

                //这是增加到xxx角色，可以用hasRole("xxx")来判断；需要注意所有的角色在这里增加时必须以ROLE_前缀，使用时则没有ROLES_前缀
                for(String role : roles) {
                    SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_" + role);
                    authorities.add(sga);
                }
                return authorities;
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getUsername() {
                return userName;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}