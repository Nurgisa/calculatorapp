package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class TimeTable implements Serializable {

    private Integer id;
    private Date date;
    private Integer countOfDay;
    private Double debtPayment;
    private Double percentPayment;
    private Double remains;
    private Double monthlyPayment;

    public TimeTable(int id, Date date, int countOfDay, double debtPayment, double percent, double remains, double monthlyPayment) {
        this.id = id;
        this.date = date;
        this.countOfDay = countOfDay;
        this.debtPayment = debtPayment;
        this.percentPayment = percent;
        this.remains = remains;
        this.monthlyPayment = monthlyPayment;
    }
}
