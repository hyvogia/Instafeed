package hyvo.prog1415.instafeed;

import java.io.Serializable;
import java.util.Date;

//Hy Vo Final Project

public class BusinessLogic implements Serializable {
    public String name;
    public float like;
    public Date reviewDate;
    public String comment;

    public BusinessLogic(String name, float like, Date reviewDate, String comment){
        this.name = name;
        this.like = like;
        this.reviewDate = reviewDate;
        this.comment = comment;
    }

    public String toString()
    {
        return name + " \nRating: " + like + " \nComment: " + comment + " \nPosted Date: " + reviewDate + "\n";
    }
}
