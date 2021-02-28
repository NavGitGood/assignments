package com.flight.system;

import com.flight.system.util.Utilities;

import java.text.DecimalFormat;
import java.util.Date;
/**
 * The Flight class defines the structure of a flight
 */
public class Flight {
    private String flight_no;
    private String dep_loc;
    private String arr_loc;
    private Date valid_till;
    private String flight_time;
    private float flight_dur;
    private Integer fare;
    private boolean seat_avl;

    public Flight(String flight_no, String dep_loc, String arr_loc, Date valid_till, String flight_time, float flight_dur, int fare, boolean seat_avl) {
        this.flight_no = flight_no;
        this.dep_loc = dep_loc;
        this.arr_loc = arr_loc;
        this.valid_till = valid_till;
        this.flight_time = flight_time;
        this.flight_dur = flight_dur;
        this.fare = fare;
        this.seat_avl = seat_avl;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public String getDep_loc() {
        return dep_loc;
    }

    public String getArr_loc() {
        return arr_loc;
    }

    public Date getValid_till() {
        return valid_till;
    }

    public String getFlight_time() {
        return flight_time;
    }

    public float getFlight_dur() {
        return flight_dur;
    }

    public Integer getFare() {
        return fare;
    }

    public boolean isSeat_avl() {
        return seat_avl;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flight_no='" + flight_no + '\'' +
                ", dep_loc='" + dep_loc + '\'' +
                ", arr_loc='" + arr_loc + '\'' +
                ", valid_till='" + Utilities.dateFormatter(valid_till) + '\'' +
                ", flight_time='" + flight_time + '\'' +
                ", flight_dur='" + flight_dur + " hrs\'" +
                ", fare='" + "INR " + new DecimalFormat("##,##,###").format(fare) + '\'' +
                ", seat_avl='" + (seat_avl ? "Yes" : "No") + '\'' +
                '}';

    }

    @Override
    public boolean equals(Object obj) {
        return !super.equals(obj);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
