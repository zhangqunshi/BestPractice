
import java.io.*;
import java.util.*;

/**
 * 用于分析日志文件，找出所有的错误日志，并且可以把相同的错误日志合并
 * @author zhangqunshi@126.com
 */

public class AnalysisLog {

    private void doMerge(List<ErrorEntity> errors) {
        Set set = new HashSet();
        for (int i = 0; i < errors.size(); i++) {
            boolean same = false;
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                ErrorEntity element = (ErrorEntity) iterator.next();
                if (element.equals(errors.get(i))) {
                    same = true;
                    break;
                }
            }

            if (same) {
                continue;
            }
            set.add(errors.get(i));

        }
        errors.clear();
        errors.addAll(set);
        System.out.println("Non-Duplicate error: " + errors.size());
    }

    class LogFileFilter implements FileFilter {
        // only allow the xxx.log file

        public boolean accept(File pathname) {
            if (pathname.isFile()) {
                String name = pathname.getName();
                String suffix = name.substring(name.lastIndexOf("."));
                if ("log".equals(suffix)) {
                    return true;
                }

            }
            return false;
        }
    }

    class ErrorEntity {

        List<String> errLines = new ArrayList<>();

        @Override
        public boolean equals(Object obj) {
            ErrorEntity other = (ErrorEntity) obj;
            if (this.errLines.size() >= 2
                    && other.errLines.size() >= 2
                    && this.errLines.get(1).equals(other.errLines.get(1))) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + Objects.hashCode(this.errLines);
            return hash;
        }

        public String toLines() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < errLines.size(); i++) {
                sb.append(errLines.get(i)).append("\n");
            }
            return sb.toString();
        }
    }

    public boolean hasError(String line) {
        if (line.contains("Error")
                || line.contains("Err")
                || line.contains("err")
                || line.contains("error")
                || line.contains("Critical")
                || line.contains("critical")) {
            return true;
        }
        return false;
    }

    public List<ErrorEntity> getErrorLines(File logFile, int extraLineCount) throws Exception {
        List<ErrorEntity> lines = new ArrayList<ErrorEntity>();
        LineNumberReader reader = new LineNumberReader(new FileReader(logFile));
        String line = null;

        long errorCount = 0;
        while ((line = reader.readLine()) != null) {
            if (hasError(line)) {
                errorCount++;

                ErrorEntity ee = new ErrorEntity();
                ee.errLines.add(line);

                // read more line
                for (int i = 0; i < extraLineCount; i++) {
                    line = reader.readLine();
                    ee.errLines.add(line);
                }
                lines.add(ee);
            }
        }

        System.out.println("Total find errors: " + errorCount);

        return lines;
    }

    public void writeToFile(String outputFile, List<ErrorEntity> content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFile)));
            for (int i = 0; i < content.size(); i++) {
                bw.write(content.get(i).toLines() + "\n\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File[] getFiles(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return null;
        }

        if (f.isDirectory()) {
            return f.listFiles(new LogFileFilter());
        }

        // if just a file
        return new File[]{f};
    }

    public void doAnalysis(String basePath, String outputFile, boolean mergeSameError) throws Exception {
        File[] logFiles = getFiles(basePath);
        if (logFiles == null) {
            System.out.println("No any log file in path: " + basePath);
        }

        List<ErrorEntity> errors = new ArrayList<>();

        for (int i = 0; i < logFiles.length; i++) {
            List<ErrorEntity> lines = getErrorLines(logFiles[0], 5);
            errors.addAll(lines);
        }

        if (mergeSameError) {
            doMerge(errors);
        }

        writeToFile(outputFile, errors);

        System.out.println("OK! please check the file: " + outputFile);
    }

    public static void main(String[] args) throws Exception {
        AnalysisLog x = new AnalysisLog();
        if (args.length == 2) {
            x.doAnalysis(args[0], args[1], true);
        } else if (args.length == 3) {
            boolean merge = Boolean.valueOf(args[2]);
            x.doAnalysis(args[0], args[1], merge);
        } else {
            System.out.println("Usage: java AnalysisLog <logPath> <outputFile> [needMergeSameError]");
        }

    }

}
