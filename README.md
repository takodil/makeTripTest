
1. Install maven:

    ```sh
    $ brew install maven
    ```

2. Install dependencies

    ```sh
    $ mvn install
    ```

3. Here’s how you run test locally with default browser chrome you can change browser name to firefox

    ```sh
    $ mvn clean test -P e2e -Dbrowser_env=local -Dbrowser=chrome
    ```
   
4. Here’s how you run test remotely with docker
    1. Have docker install on your machine
    2. Start docker
    3. Run this command

    ```sh
    $ mvn clean test -P e2e -Dbrowser_env=remote -Dbrowser=chrome
    ```
   
5. Here’s how you open report

    ```sh
    $ allure serve target/allure-results
    ```  