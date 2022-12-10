import com.learnjava.parallelstreams.ParallelStreamsExample;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void stringTransform(){

        //given
        List<String> inputList = DataSet.namesList();

        //when
        startTimer();
        List<String> resultList = parallelStreamsExample.stringTransform(inputList);
        timeTaken();

        //then
        assertEquals(4, resultList.size());
        resultList.forEach(name->{
            assertTrue(name.contains("-"));
        });

    }
}
