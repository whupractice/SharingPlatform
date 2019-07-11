package com.example.demo.controller;

import com.example.demo.entity.StudentEntity;
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
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
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

        UserDetails userDetails = createUser("123","456",new String[]{"student"});

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String id = (String) args[0];
            Assert.assertEquals(id,"123");
            bitSet.set(0, true);
            return userDetails;
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
    public void login() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setPwd("456");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long phone = (long) args[0];
            Assert.assertEquals(phone,123);
            bitSet.set(0, true);
            return studentEntity;
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        String jsonData = "{\"phone\":\"123\",\"pwd\":\"456\",\"isManager\":\"0\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/student/login")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void logout() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/logout")
                .with(request -> {
                    request.addHeader("Authorization","123");
                    return request;
                }))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getNickNameByPhone() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setNickName("456");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long phone = (long) args[0];
            Assert.assertEquals(phone,123);
            bitSet.set(0, true);
            return studentEntity;
        }).when(studentRepository).getNickNameByPhone(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getNickName")
                .param("phone","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void register() throws Exception {
        BitSet bitSet = new BitSet(4);
        bitSet.set(0, false);
        bitSet.set(1, false);
        bitSet.set(2, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setPwd("456");
        studentEntity.setNickName("123");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long phone = (long) args[0];
            Assert.assertEquals(phone,123);
            if(!bitSet.get(0)){
                bitSet.set(0, true);
                return null;
            }else {
                return studentEntity;
            }
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String nickName = (String) args[0];
            Assert.assertEquals(nickName,"123");
            bitSet.set(1, true);
            return null;
        }).when(studentRepository).getStuByNickName(Mockito.any(String.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            StudentEntity student = (StudentEntity) args[0];
            Assert.assertEquals(student.getNickName(),"123");
            Assert.assertEquals(student.getPwd(),"456");
            bitSet.set(2, true);
            return null;
        }).when(studentRepository).save(Mockito.any(StudentEntity.class));

        String jsonData = "{\"phone\":\"123\",\"pwd\":\"456\",\"isManager\":\"0\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/student/register")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
    }

    @Test
    public void registerLessonManager() throws Exception {
        BitSet bitSet = new BitSet(4);
        bitSet.set(0, false);
        bitSet.set(1, false);
        bitSet.set(2, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setPwd("456");
        studentEntity.setNickName("123");
        studentEntity.setIsLessonManager(1);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long phone = (long) args[0];
            Assert.assertEquals(phone,123);
            if(!bitSet.get(0)){
                bitSet.set(0, true);
                return null;
            }else {
                return studentEntity;
            }
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String nickName = (String) args[0];
            Assert.assertEquals(nickName,"123");
            bitSet.set(1, true);
            return null;
        }).when(studentRepository).getStuByNickName(Mockito.any(String.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            StudentEntity student = (StudentEntity) args[0];
            Assert.assertEquals(student.getNickName(),"123");
            Assert.assertEquals(student.getPwd(),"456");
            bitSet.set(2, true);
            return null;
        }).when(studentRepository).save(Mockito.any(StudentEntity.class));

        String jsonData = "{\"phone\":\"123\",\"pwd\":\"456\",\"isLessonManager\":\"1\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/student/registerLessonManager")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
    }

    @Test
    public void registerManager() throws Exception {
        BitSet bitSet = new BitSet(4);
        bitSet.set(0, false);
        bitSet.set(1, false);
        bitSet.set(2, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setPwd("456");
        studentEntity.setNickName("123");
        studentEntity.setIsManager(1);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long phone = (long) args[0];
            Assert.assertEquals(phone,123);
            if(!bitSet.get(0)){
                bitSet.set(0, true);
                return null;
            }else {
                return studentEntity;
            }
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String nickName = (String) args[0];
            Assert.assertEquals(nickName,"123");
            bitSet.set(1, true);
            return null;
        }).when(studentRepository).getStuByNickName(Mockito.any(String.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            StudentEntity student = (StudentEntity) args[0];
            Assert.assertEquals(student.getNickName(),"123");
            Assert.assertEquals(student.getPwd(),"456");
            bitSet.set(2, true);
            return null;
        }).when(studentRepository).save(Mockito.any(StudentEntity.class));

        String jsonData = "{\"phone\":\"123\",\"pwd\":\"456\",\"isManager\":\"1\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/student/registerManager")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void getStuById() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setNickName("别摸我");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long id = (long)args[0];
            Assert.assertEquals(id,123);
            bitSet.set(0, true);
            return studentEntity;
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/info")
                .param("phone","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("别摸我")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void getStuByNickName() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setNickName("别摸我");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String nickName = (String)args[0];
            Assert.assertEquals(nickName,"别摸我");
            bitSet.set(0, true);
            return studentEntity;
        }).when(studentRepository).getStuByNickName(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/nickName")
                .param("nickName","别摸我"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("别摸我")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getStuBySchoolName() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<StudentEntity> studentEntities = new ArrayList<>();
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setNickName("别摸我");
        studentEntity.setSchoolName("加里敦大学");
        studentEntities.add(studentEntity);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String schoolName = (String)args[0];
            Assert.assertEquals(schoolName,"加里敦大学");
            bitSet.set(0, true);
            return studentEntities;
        }).when(studentRepository).getStuBySchoolName(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/schoolName")
                .param("schoolName","加里敦大学"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("别摸我")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("加里敦大学")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    public void getAllStudent() throws Exception {
        List<StudentEntity> studentEntities = new ArrayList<>();
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setNickName("别摸我");
        studentEntities.add(studentEntity);
        Mockito.when(studentRepository.findAll()).thenReturn(studentEntities);
        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("123")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("别摸我")));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void getAllManager() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        List<StudentEntity> studentEntities = new ArrayList<>();
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(456);
        studentEntity.setIsManager(1);
        studentEntities.add(studentEntity);

        Mockito.doAnswer(invocationOnMock -> {
            bitSet.set(0, true);
            return studentEntities;
        }).when(studentRepository).getAllManager();

        mockMvc.perform(MockMvcRequestBuilders.get("/student/manager"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("456")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")));

        Assert.assertTrue(bitSet.get(0));
    }

    @Test
    @WithMockUser(roles={"student"})
    public void updateStudent() throws Exception {
        BitSet bitSet = new BitSet(4);
        bitSet.set(0, false);
        bitSet.set(1, false);
        bitSet.set(2, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setPwd("456");
        studentEntity.setNickName("123");

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long phone = (long) args[0];
            Assert.assertEquals(phone,123);
            bitSet.set(0, true);
            return studentEntity;
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String nickName = (String) args[0];
            Assert.assertEquals(nickName,"123");
            bitSet.set(1, true);
            return null;
        }).when(studentRepository).getStuByNickName(Mockito.any(String.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            StudentEntity student = (StudentEntity) args[0];
            Assert.assertEquals(student.getNickName(),"123");
            Assert.assertEquals(student.getPhone(),123);
            Assert.assertEquals(student.getPwd(),"456");
            bitSet.set(2, true);
            return null;
        }).when(studentRepository).save(Mockito.any(StudentEntity.class));

        String jsonData = "{\"phone\":\"123\",\"pwd\":\"456\",\"nickName\":\"123\",\"isManager\":\"0\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/student/updateStudent")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
    }

    @Test
    @WithMockUser(roles={"lessonManager"})
    public void updateLessonManager() throws Exception {
        BitSet bitSet = new BitSet(4);
        bitSet.set(0, false);
        bitSet.set(1, false);
        bitSet.set(2, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setPwd("456");
        studentEntity.setNickName("123");
        studentEntity.setIsLessonManager(1);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long phone = (long) args[0];
            Assert.assertEquals(phone,123);
            bitSet.set(0, true);
            return studentEntity;
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String nickName = (String) args[0];
            Assert.assertEquals(nickName,"123");
            bitSet.set(1, true);
            return null;
        }).when(studentRepository).getStuByNickName(Mockito.any(String.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            StudentEntity student = (StudentEntity) args[0];
            Assert.assertEquals(student.getNickName(),"123");
            Assert.assertEquals(student.getPhone(),123);
            Assert.assertEquals(student.getPwd(),"456");
            bitSet.set(2, true);
            return null;
        }).when(studentRepository).save(Mockito.any(StudentEntity.class));

        String jsonData = "{\"phone\":\"123\",\"pwd\":\"456\",\"nickName\":\"123\",\"isLessonManager\":\"1\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/student/updateLessonManager")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void updateManager() throws Exception {
        BitSet bitSet = new BitSet(4);
        bitSet.set(0, false);
        bitSet.set(1, false);
        bitSet.set(2, false);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPhone(123);
        studentEntity.setPwd("456");
        studentEntity.setNickName("123");
        studentEntity.setIsManager(1);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            long phone = (long) args[0];
            Assert.assertEquals(phone,123);
            bitSet.set(0, true);
            return studentEntity;
        }).when(studentRepository).getStuById(Mockito.any(long.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String nickName = (String) args[0];
            Assert.assertEquals(nickName,"123");
            bitSet.set(1, true);
            return null;
        }).when(studentRepository).getStuByNickName(Mockito.any(String.class));

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            StudentEntity student = (StudentEntity) args[0];
            Assert.assertEquals(student.getNickName(),"123");
            Assert.assertEquals(student.getPhone(),123);
            Assert.assertEquals(student.getPwd(),"456");
            bitSet.set(2, true);
            return null;
        }).when(studentRepository).save(Mockito.any(StudentEntity.class));

        String jsonData = "{\"phone\":\"123\",\"pwd\":\"456\",\"nickName\":\"123\",\"isManager\":\"1\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/student/updateManager")
                .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void deleteStudent() throws Exception {
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, false);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            Assert.assertEquals((long)args[0],123);
            bitSet.set(0, true);
            return null;
        }).when(studentRepository).deleteById(Mockito.any(long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/student")
                .param("phone","123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(bitSet.get(0));

    }

    @Test
    @WithMockUser(roles={"manager"})
    public void getLessonManagerPages() {
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void getLessonManagerPagesByName() {
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void getLessonManagerPagesBySchool() {
    }

    @Test
    @WithMockUser(roles={"student"})
    public void getSkillImg() {
    }

    @Test
    @WithMockUser(roles={"manager"})
    public void getAllGraph() {
    }

    @Test
    @WithMockUser(roles={"student"})
    public void uploadImg() {

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