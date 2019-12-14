package com.bag.animalfacts;

public class AnimalFact {

        private String nameAnimal;
        private String data;
        private String fact;
        private int upvotes;

        public AnimalFact(String name, String data, String fact, String upvotes) {
            this.nameAnimal = name;
            this.data = data.substring(0,10);
            this.fact = fact;
            this.upvotes =  Integer.parseInt(upvotes);
        }

        public String getNameAnimal() {
            return nameAnimal;
        }
        public String getData() {
            return data;
        }
        public String getFact() { return fact; }
        public int getUpvotes() { return upvotes; }
        public String getUpvotesString() { return Integer.toString(upvotes); }


        public void setData(String data) {
            this.data=data;
        }
        public void setNameAnimal(String nameAnimal) {
            this.nameAnimal = nameAnimal;
        }
        public void setFact(String fact) {
            this.fact=fact;
        }
        public void setUpvotes(int upvotes) { this.upvotes= upvotes; }

    public String toString() {
        return this.nameAnimal;
    }
}
