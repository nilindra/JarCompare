package org.jarcompare.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Nilindra.Fernando
 */
public class PerfUtils {

    private PerfUtils() {
    }

    public static String getExecutionTime(Date startDateTime, Date endDateTime) {
        // Get difference in milliseconds
        long diffMillis = endDateTime.getTime() - startDateTime.getTime();
        BigDecimal diffSeconds = (new BigDecimal(Long.toString(diffMillis))).divide(new BigDecimal("1000"), 2,
                BigDecimal.ROUND_UP);

        return " (processed in " + diffSeconds + " seconds)";
    }
}
