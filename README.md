
1. Install maven:

    ```sh
    $ brew install maven
    ```

2. Install dependencies

    ```mvn install
    ```

3. Here’s how you run test locally with default browser chrome

    ```sh
    $ mvn clean test -P e2e -Dbrowser_env=local
    ```