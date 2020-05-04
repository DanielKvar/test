package events;

import com.example.structure.Tekma;
import org.greenrobot.eventbus.EventBus;
import java.time.LocalDate;

public class MyEventNewTekma {

    public static void NewTekma(Tekma tekma){
        EventBus.getDefault().postSticky(tekma);
    }

    public static void NewTekma(String naslov, String opis, LocalDate startDate, LocalDate endDate, Boolean important){
        EventBus.getDefault().postSticky(new Tekma(naslov, opis, startDate, endDate, important));
    }
}
