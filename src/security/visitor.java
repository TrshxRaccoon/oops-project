package security;

import users.User;
import java.time.LocalDateTime;

public class visitor extends user
{
    protected String ID; //visitors ID
    protected boolean authorisedToEnter;
    public static class logs
    {
        protected String dateAndTime;
        protected String visiteeId; //Id of resident being visited
        protected String type; // Entry or exit or unauth
        protected boolean authorised; 

        public logs() 
        {
            LocalDateTime now = LocalTimeDate.now()
            this.dateAndTime = String.toString(now);
            this.visiteeId = null;
            this.authorised = false;
            this.visiteeId = null;
        }

        public logs(String dateAndTime , String visiteeId , String type) 
        {
            this.dateAndTime = dateAndTime;
            this.visiteeId = null;
            this.authorised = false;
            this.visiteeId = null;
        }
    }
}