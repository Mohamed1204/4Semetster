using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace UniversityAdministration.Models.Entities
{
    public class Course
    {
        public int CourseId { get; set; }
        public string Title { get; set; }
        public int Etcs { get; set; }

        // Navigation property
        public ICollection<Enrollment> Enrollments { get; set; }
    }
}
