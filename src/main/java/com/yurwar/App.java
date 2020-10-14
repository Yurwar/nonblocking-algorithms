package com.yurwar;

import com.yurwar.service.ArrayService;
import com.yurwar.service.DefaultArrayService;

public class App {
    private static final int DEFAULT_TEST_ARRAY_SIZE = 10000;

    public static void main(String[] args) {
        ArrayService arrayService = new DefaultArrayService();
        arrayService.processArray(parseSizeFromCommandLine(args));
    }

    private static int parseSizeFromCommandLine(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
            }
        }
        return DEFAULT_TEST_ARRAY_SIZE;
    }
}
