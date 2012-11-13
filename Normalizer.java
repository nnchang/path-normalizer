class Normalizer {
    
    /** Runs some tests on the Normalizer. */
    public static void main(String[] args) {
        String[] tests = {"foo/./bar", "foo/bar/../baz", "foo//bar",
                          "curiosity/killed/../../the/cat", "./foo/bar",
                          "../odd/test", "/./foo/bar", "/../odd/test"};
        String[] solns = {"foo/bar", "foo/baz", "foo//bar", "the/cat",
                          "foo/bar", "odd/test", "foo/bar", "odd/test"};
        String testOutput;
        boolean failed = false;

        System.out.println("starting tests.");
        System.out.println();

        for (int i = 0; i < tests.length; i++) {
            testOutput = normalize(tests[i]);
            System.out.printf("testing %s, expect %s", tests[i], solns[i]);
            if (!testOutput.equals(solns[i])) {
                System.out.println(" FAILED.");
                System.err.printf("test input %s expected output %s, got %s\n",
                                  tests[i], solns[i], testOutput);
                failed = true;
            } else {
                System.out.println(" success!");
            }
        }

        System.out.println();
        if (failed) {
            System.out.println("not all tests passed.");
        } else {
            System.out.println("test success!");
        }
    }

    /** Given an input string representing a filename, return a string with
     *  single-dot components removed; also, double-dot components are removed,
     *  along with their parent directory if present.  Does not work correctly
     *  with Windows-style backslash-separated paths. */
    private static String normalize(String path) {
        String ret = path;

        //deal with things at the beginning of the line.
        if (ret.startsWith("/")) {
            ret = ret.substring(1);
        }
        if (ret.startsWith("./")) {
            ret = ret.substring(2);
        }

        // deal with the other things.
        while (ret.matches(".*/\\.\\./.*")
               || ret.contains("/./")) {
            ret = ret.replaceFirst("[^/]*/\\.\\.[$/]", "");
            ret = ret.replaceAll("/\\.[$/]", "/");
            ret = ret.replaceAll("/[^/]*/\\.\\.[$/]", "/");
        }

        ret = ret.replace("../", "");
        return ret;
    }

}
