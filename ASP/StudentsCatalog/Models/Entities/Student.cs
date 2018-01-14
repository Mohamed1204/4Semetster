using System.ComponentModel.DataAnnotations;

namespace StudentsCatalog.Models.Entities
{
    public class Student
    {
        public int StudentId { get; set; }
        
        [Required (ErrorMessage = "Du skal udfylde dette felt!!")]
        [Display(Name = "First Name")]
        public string FirstName { get; set; }
        
        [Display(Name = "YYYYYY Name")]
        public string LastName { get; set; }
        public int Age { get; set; }

    }

}


