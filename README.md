# Hou to use this library

1. 최신 release 버전을 클릭합니다.
   
![step1](https://github.com/user-attachments/assets/735c57b2-94ea-4d83-9b73-e3de620f7974)

---

2. 해당 버전의 .jar 파일을 다운로드 받습니다.
  
![step2](https://github.com/user-attachments/assets/ae392316-40ee-4ed5-a823-41d6f375ed30)

---

3. jar 파일을 사용할 프로젝트 내부에 삽입해줍니다. (libs 폴더 하위에 위치시킴)
  
![step3](https://github.com/user-attachments/assets/1e765ef5-3bee-4395-980f-6d6d24385600)

---

4. (gradle 기준) build.gradle에 의존성을 주입합니다.
    - repositories : flatDir(dirs: "jar파일이 있는 폴더명")
    - dependencies : implementation name: "jar파일의 이름(확장자X)"
![step4](https://github.com/user-attachments/assets/bcc1cadc-3a02-4c86-8b1e-0a6a2b53d066)

---

5. 빌드가 완성되면 jar 파일 내부의 class들을 import 하고 메서드를 사용합니다.
   - uploader : "파일의절대경로\\파일명.확장자", (local 사용 시 : "web서버주소:8083" )
   - fileName : uploader.upload (uploader의 파일을 업로드합니다.)
![stsep5](https://github.com/user-attachments/assets/7d80c41c-8a93-4ae3-b9b4-df45e1f56767)

---

6-1. 파일 업로드 성공 시
   
![step6](https://github.com/user-attachments/assets/60332bb6-8a71-4701-af11-5a37ce877d84)
  
  

6-2 파일 업로드 실패 시 (파일경로 및 파일명 문제일 가능성이 높습니다. 혹은 서버의 주소 및 포트를 확인해주세요.)

![step6-2](https://github.com/user-attachments/assets/279d045b-a176-469f-a059-5741de8f7de0)

---
