package ru.sfedu.my_pckg.lab5.model;



import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name="REVIEW_LAB5")
public class Review implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private long id;
        @NotNull
        private int rating;
        private String comment;


        @OneToOne(mappedBy = "courseReview")
        private Course course;
        //
        // Constructors
        //
        public Review () { };


        /**
         * Set the value of id
         * @param id the new value of id
         */
        public void setId (long id) { this.id = id; }

        /**
         * Get the value of id
         * @return the value of id
         */
        public long getId () {
            return id;
        }

        /**
         * Set the value of rating
         * @param rating the new value of rating
         */
        public void setRating (int rating) {
            this.rating = rating;
        }

        /**
         * Get the value of rating
         * @return the value of rating
         */
        public int getRating () {
            return rating;
        }

        /**
         * Set the value of comment
         * @param comment the new value of comment
         */
        public void setComment (String comment) { this.comment = comment; }

        /**
         * Get the value of comment
         * @return the value of comment
         */
        public String getComment () {
            return comment;
        }

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        //
        // Other methods
        //
        @Override
        public String toString(){
            return " \nReview : { "+
                    "\nid: " + getId() +
                    "\nrating: " + getRating() +
                    "\ncomment: " + getComment() +
                    "\n}";
        }
        @Override
        public int hashCode(){
            return Objects.hash(getId(),getRating(),getComment(),getCourse());
        }

        @Override
        public boolean equals(Object Obj){
            if (this == Obj) return true;
            if (Obj == null || getClass() != Obj.getClass()) return false;
            Review review = (Review) Obj;
            if (getId() != review.getId()) return false;
            if (!getComment().equals(review.getComment())) return false;
            if (!getCourse().equals(review.getCourse())) return false;
            if (getRating() != review.getRating()) return false;
            return true;
        }


}
