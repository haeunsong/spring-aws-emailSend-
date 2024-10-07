# AWS SES 이메일 전송 시스템

## 1. 사용 기술 스택
- Spring
- [AWS] Amazon Simple Email Service
- [AWS] IAM



## 2. AWS 구성 방법
AWS SES 를 Spring 프로젝트에서 사용하려면 IAM 을 통해 자격증명을 설정하고, AWS CLI 를 통해 템플릿을 연동해야 한다.
SES 에서 먼저 jamy022576@gmail.com, jamy0225@naver.com 두 개의 이메일의 관한 자격증명을 얻었다.
![image](https://github.com/user-attachments/assets/d29a0fe1-b084-4301-a5af-04fc8be919a8)
테스트 부분에서 jamy022576@gmail.com 유저가 jamy0225@naver.com 유저로 OTP 인증 이메일을 전송할 것이다.



### 2.1 IAM 구성
- IAM 사용자를 생성한다.
- AmazonSESFUllAccess 정책을 생성한다.
  ![image](https://github.com/user-attachments/assets/20eef347-3208-477f-a1f4-ad00e96fb881)
- 사용자 생성 후 AccessKey 와 SecretKey 저장(보관)한다.
  ![image](https://github.com/user-attachments/assets/0c25fd66-509e-40e8-8e47-db3e43f8191c)
  

### 2.2 AWS CLI 구성
- AWS CLI 를 설치 한 후, `$ aws configure` 명령어로 AWS CLI 를 구성한다.
- 이 때, IAM에서 사용자 생성 후 받은 AccessKey 와 SecretKey 를 집어넣고, region 도 올바르게 설정한다.


### 2.3 SES 템플릿 생성
`$ aws ses create-template --cli-input-json file://template.json`
```json
{
  "Template": {
    "TemplateName": "invite-opt-code",
    "SubjectPart": "회원 가입을 축하합니다!!",
    "HtmlPart": "<html>\n  <body>\n    <div>\n      <p>안녕하세요? {{email}}님! 환영합니다</p>\n    </div>\n    <div>\n      여기를 통해 OTP를 등록해주세요!!\n      <img src=\"{{link}}\" alt=\"QR Code\"/>\n    </div>\n  </body>\n</html>"
  }
}
```

프로젝트 폴더 안에 생성한 'template' 을 aws ses 와 연동해주는 과정이다. 여기서는 'invite-opt-code' 라는 templateName 을 사용했다.


## 3. Swagger 를 사용하여 테스트
http://localhost:7002/swagger-ui/index.html#/Account%20API/sendOTP

<img width="1250" alt="image" src="https://github.com/user-attachments/assets/0a2d5109-940c-4368-aa5b-b4ed07ac2331">
RequestBody 부분에 수신자 이메일을 넣어주고 [Execute] 를 눌러 실행한다.

<img width="1217" alt="image" src="https://github.com/user-attachments/assets/b41a21bf-6d56-4c34-9b62-24f826d95651">
<img width="1223" alt="image" src="https://github.com/user-attachments/assets/c89ee799-ebaf-4173-ac7d-b2c68ecb9727">
<img width="384" alt="image" src="https://github.com/user-attachments/assets/80ae2d5f-54b7-4656-89bc-9fddd9029658">

## 4. 테스트 결과
<img width="566" alt="image" src="https://github.com/user-attachments/assets/544b63ab-52ee-404e-8af1-12cef31fcaba">

## 5. 참고
https://www.inflearn.com/course/spring-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%A0%84%EC%86%A1-%EC%8B%9C%EC%8A%A4%ED%85%9C/dashboard

