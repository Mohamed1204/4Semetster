using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace UniversityAdministration.Models.Entities
{
    public class Student
    {
        public int StudentId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime EnrollmentDate { get; set; }

        // Navigation property
        // Når jeg læser en studerende vil jeg gerne se hvilke kurser han er tilmeldt
        public ICollection<Enrollment> Enrollments { get; set; }
    }
}
