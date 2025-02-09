package com.coderscampus.assignment;

public class AsyncNumberFetchingApplication {

    public static void main(String[] args) {
        AsyncNumberFetchingService asyncNumberFetchingService = new AsyncNumberFetchingService();
        asyncNumberFetchingService.fetchNumbers();
        asyncNumberFetchingService.countAndPrintNumbers();
        asyncNumberFetchingService.shutdown();
    }
}
