
1. Install maven:

    ```sh
    $ brew install maven
    ```

2. Install dependencies

    ```sh
    $ mvn install
    ```

3. Here’s how you run test locally with default browser chrome

    ```sh
    $ mvn clean test -P e2e -Dbrowser_env=local
    ```
   
4. Here’s how you open report

    ```sh
    $ allure serve target/allure-results
    ```  