<template>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick" class="tabs-wrapper">
    <el-tab-pane label="登录页一" name="first">
      <div class="login">
        <div class="login-logo-img"></div>
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <h3 class="title">用户登录</h3>
          <span class="other-text">你若不离不弃，我必生死相依</span>
          <el-form-item label="账号">
            <el-input
            label-position="top"
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="请输入账号"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input
            label-position="top"
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="请输入密码"
              @keyup.enter.native="handleLogin"
            >
            </el-input>
          </el-form-item>
          <el-form-item v-if="captchaOnOff" label="验证码">
            <el-input
              label-position="top"
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              @keyup.enter.native="handleLogin"
            >
            </el-input>
            <div class="login-code">
              <img :src="codeUrl" @click="getCode" class="login-code-img"/>
            </div>
          </el-form-item>
          <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
          <el-form-item style="width:100%;">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width:100%;padding: 15px 20px;font-size: 16px;"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <div style="float: right;" v-if="register">
              <router-link class="link-type" :to="'/register'">立即注册</router-link>
            </div>
          </el-form-item>
        </el-form>
        <!--  底部  -->
        <!-- 侧栏 -->
        <div class="side-bg">
          <h4 class="side-text">欢迎使用-若依后台管理系统</h4>
          <ul class="side-item">
            <li><i class="el-icon-right"></i> SpringBoot</li>
            <li><i class="el-icon-right"></i> Vue</li>
            <li><i class="el-icon-right"></i> MyBatis</li>
            <li><i class="el-icon-right"></i> Element-ui</li>
            <li><i class="el-icon-right"></i> Druid</li>
            <li><i class="el-icon-right"></i> Sass</li>
          </ul>
        </div>
        <div class="el-login-footer">
          Copyright © 2018-2022 ruoyi.vip All Rights Reserved.
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane label="登录页二" name="second" class="wrapper-box02">
      <div class="login">
        <div class="side-bg leftsidebg">
          <h4 class="side-text">欢迎使用-若依后台管理系统</h4>
          <ul class="side-item">
            <li><i class="el-icon-right"></i> SpringBoot</li>
            <li><i class="el-icon-right"></i> Vue</li>
            <li><i class="el-icon-right"></i> MyBatis</li>
            <li><i class="el-icon-right"></i> Element-ui</li>
            <li><i class="el-icon-right"></i> Druid</li>
            <li><i class="el-icon-right"></i> Sass</li>
          </ul>
                      <div class="more-btn-box">
                                      <a href="#" class="more-btn">了解更多</a>
                                  </div>
        </div>
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <div class="login-logo-img02"></div>
          <h3 class="title">用户登录</h3>
          <span class="other-text">你若不离不弃，我必生死相依</span>
          <el-form-item>
            <el-input
            label-position="top"
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="请输入账号"
            >
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
            label-position="top"
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="请输入密码"
              @keyup.enter.native="handleLogin"
            >
            </el-input>
          </el-form-item>
          <el-form-item v-if="captchaOnOff">
            <el-input
              label-position="top"
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              @keyup.enter.native="handleLogin"
            >
            </el-input>
            <div class="login-code">
              <img :src="codeUrl" @click="getCode" class="login-code-img"/>
            </div>
          </el-form-item>
          <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
          <el-form-item style="width:100%;">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width:100%;padding: 20px;font-size: 20px;border-radius: 8px;"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <div style="float: right;" v-if="register">
              <router-link class="link-type" :to="'/register'">立即注册</router-link>
            </div>
          </el-form-item>
        </el-form>
        <!--  底部  -->
        <!-- 侧栏 -->

        <div class="el-login-footer">
          Copyright © 2018-2022 ruoyi.vip All Rights Reserved.
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane label="登录页三" name="third" class="wrapper-box03">
      <div class="login">
        <div class="login-logo">
          <div class="login-logo-img03"></div>
          欢迎使用-若依后台管理系统
          </div>
          <div class="login-box03">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <h2 class="login-text">用户登录</h2>
          <el-form-item>
            <el-input
            label-position="top"
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="请输入账号"
            >
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
            label-position="top"
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="请输入密码"
              @keyup.enter.native="handleLogin"
            >
            </el-input>
          </el-form-item>
          <el-form-item v-if="captchaOnOff">
            <el-input
              label-position="top"
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              @keyup.enter.native="handleLogin"
            >
            </el-input>
            <div class="login-code">
              <img :src="codeUrl" @click="getCode" class="login-code-img"/>
            </div>
          </el-form-item>
          <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
          <el-form-item style="width:100%;">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width:100%;padding: 15px;font-size: 20px;font-weight: bold;border-radius: 50px;"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <div style="float: right;" v-if="register">
              <router-link class="link-type" :to="'/register'">立即注册</router-link>
            </div>
          </el-form-item>
        </el-form>
        <!--  底部  -->
        <!-- 侧栏 -->
        <div class="login-pic03"></div>
</div>
        <div class="el-login-footer">
          Copyright © 2018-2022 ruoyi.vip All Rights Reserved.
        </div>
      </div>
    </el-tab-pane>
  </el-tabs>

</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      activeName: 'first',
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaOnOff: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaOnOff) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
  .tabs-wrapper{
    width: 100%;
    height: 100%;
    .el-tabs__header{
      position: absolute;
      top:16px;
      right:20px;
      border-bottom:0;
      .el-tabs__nav{
        border: 0;
      }
      .el-tabs__item{
        background-color: rgba(0,0,0,0.4);
        border-radius: 4px;
        text-align: center;
        font-size: 14px;
        color: #ffffff;
        margin: 0 6px;
        border: 0;
        height: 36px;
        padding: 0px 16px;
        line-height: 36px;
      }
    }
    .el-tabs__content{
      width: 100%;
      height: 100%;
      .el-tab-pane{
        width: 100%;
        height: 100%;
      }
    }
  }
.login {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  min-height: 750px;
  min-width: 1300px;
  /* background-image: url("../assets/images/login-background.jpg");
  background-size: cover; */
}
.title {
  margin: 0px auto 8px auto;
  text-align: left;
  font-size: 22px;
      font-weight: bold;
      color: #202329;
}
.other-text{
font-size: 16px;
    color: #646a73;
    margin-bottom: 30px;
    display: inline-block;
}
.login-logo-img{
  background: url("../assets/logo/logo.png") no-repeat left top;
  position: absolute;
  left:20px;
  top:10px;
  width: 64px;
  height: 64px;
}
.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 450px;
  margin: 0 auto;
  padding: 20px 30px;
  .el-form-item__label{
    font-size: 16px;
    line-height: 30px;
  }
  .el-input {
    height: 38px;
    input {
      height: 38px;
      border: 0;
      border-bottom: 1px solid #d9d9d9;
      padding: 0;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  position: relative;
  margin-top:-42px;
  float: right;
  text-align: right;
  img.login-code-img {
    cursor: pointer;
    vertical-align: middle;
    width: 95px;

  }
}
.el-login-footer {
  height: 60px;
  line-height: 60px;
  position: fixed;
  bottom: 0;
  right:600px;
  left:0px;
  text-align: center;
  color: #666666;
  font-size: 13px;
}
.side-bg{
  width: 600px;
  height: 100%;
  background: url("../assets/images/side-bg.png") no-repeat left top;
}
.login-tab{
	position: fixed;
	top:16px;
	right:20px;
	list-style: none;
  margin: 0;
  padding:0;
}
.login-tab li{
display: inline-block;
padding:0;
margin: 0 7px;
}
.login-tab li a.login-tab-link{
	color: #ffffff;
	background-color: rgba(0,0,0,0.4);
	padding: 6px 16px;
	border-radius: 4px;
	display: block;
	font-size:14px;
}
.side-bg .side-text{
	font-size: 30px;
	color: #ffffff;
	margin-top: 150px;
  font-weight: bold;
	display: block;
	text-align: center;
}
.side-bg .side-item{
	font-size: 20px;
	color: #ffffff;
	width: 350px;
	height: 150px;
	margin: 50px auto 0 150px;
	list-style: none;
	padding: 0;
}
.side-bg .side-item li{
	width: 50%;
	text-align: left;
	display: flex;
	align-items: center;
	float: left;
	margin: 8px 0;
}
.side-bg .side-item li i{
	font-size: 16px;
	margin-right: 5px;
}
.wrapper-box02{
  .leftsidebg{
    width: 50%;
    height: 100%;
    background: url("../assets/images/side-leftbg.png") no-repeat left top;
    .side-text{
      font-size: 40px;
      margin: 230px auto 0;
      width: 500px;
      }
      .side-item{
        font-size: 20px;
            color: rgba(255,255,255,0.8);
            width: 500px;
            height: 150px;
            margin: 50px auto 0;
            list-style: none;
            padding: 0;
      }
      .more-btn-box{
      	width: 500px;
      	margin: 60px auto 0;
      }
      .more-btn{
      	background-color: #37b1e4;
      	font-size: 22px;
      	display: inline-block;
      	padding: 12px 24px;
      	color: #ffffff;
      	border-radius: 6px;
      }
  }
  .login-form{
    width: 550px;
    padding: 0;
    .title{
      font-size: 36px;
      color: #3f4e5e;
      margin: 20px 0 10px;
    }
    .el-form-item{
      margin-bottom: 18px;
    }
    .el-input{
      height:52px;
      margin: 5px 0;
    }
    .el-input input{
      border: 1px solid #e5e5e5;
          border-radius: 4px!important;
          padding: 5px 10px!important;
          height: 50px!important;
          font-size: 16px;
    }
    .login-code{
      margin-top: -52px;
      margin-right: 12px;
    }
  }
  .login-logo-img02{
    background: url("../assets/logo/logo.png") no-repeat left top;
    width: 64px;
    height: 64px;
  }
  .el-login-footer{
    width: 50%;
    right: 0;
    left:50%;
  }
}
.wrapper-box03{
    background-color: #3269c2;
    display: flex;
    justify-content: center;
    .login {
      display: flex;
      align-items: center;
      flex-direction: column;
      justify-content: center;
      height: 100%;
      min-height: 750px;
      min-width: 1300px;
    }
    .login-logo{
      width: 880px;
      display: flex;
      align-items: center;
      margin: 0px auto 40px;
      justify-content: center;
      font-size: 28px;
      font-weight: bold;
      color: #ffffff;
      .login-logo-img03{
        background: url("../assets/logo/logo-white.png") no-repeat left top;
        width: 64px;
        height: 64px;
        margin-right: 8px;
      }

    }
  .login-box03{
    background: #ffffff;
    border-radius: 8px;
    width: 880px;
    margin: 0 auto;
    padding: 30px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 3px 6px -4px #0000001f, 0 6px 16px #00000014, 0 9px 28px 8px #0000000d!important;
    .login-form{
      width: 420px;
      padding: 20px;
      margin: 0;
    }
    .el-input{
      height: 48px;
    }
    .login-text{
      text-align: center;
      padding: 0 0 30px;
      margin-top: 0;
      font-size: 32px;
      font-weight: bold;
    }
    .el-input input{
      border: 1px solid #e5e5e5;
          padding: 5px 18px!important;
          height: 50px!important;
          font-size: 16px;
          border-radius: 50px!important;
    }
    .login-code{
      margin-top: -43px;
      margin-right: 16px;
    }
    .login-pic03{
      background: url("../assets/images/login-pic03.png") no-repeat left top;
      width: 404px;
      height: 230px;
    }
  }
  .el-login-footer{
    right: 0;
    left:0;
    color: #ffffff;
  }
}

</style>
