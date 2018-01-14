using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using StudenttAdminFinal.Data;
using StudenttAdminFinal.Models;
using Microsoft.EntityFrameworkCore;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace StudenttAdminFinal.API
{
    [Produces("application/json")]
    [Route("api/[controller]")]
    public class StudentsController : Controller
    {
        private readonly SchoolContext _context;

        public StudentsController(SchoolContext context)
        {
            _context = context;
        }
        // GET: api/values

        [HttpGet]
        public IEnumerable<Student> GetStudent()
        {
            return _context.Students;
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetStudent(int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var student = await _context.Students.SingleOrDefaultAsync(m => m.ID == id);
                            
            if(student == null)
            {
                return NotFound();
            }
            return Ok(student);
        }

        // POST: api/TodoItems
        [HttpPost]
        public async Task<IActionResult> PostStudent([FromBody] Student student)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Students.Add(student);
            await _context.SaveChangesAsync();

            return NoContent();
            //return CreatedAtAction("GetTodoItem", new { id = todoItem.TodoItemId }, todoItem);
        }

        [HttpPut("{id}")] //Fix
        public async Task<IActionResult> Update(long id, [FromBody] Student student)
        {
            if (student == null || student.ID != id)
            {
                return BadRequest();
            }

            var stud = _context.Students.FirstOrDefault(t => t.ID == id);
            if (stud == null)
            {
                return NotFound();
            }

            
            stud.FirstMidName = student.FirstMidName;
            stud.LastName = student.LastName;
            stud.EnrollmentDate = student.EnrollmentDate;

            _context.Students.Update(stud);
            await _context.SaveChangesAsync();

            return new NoContentResult();
        }
       



        // DELETE: api/Students/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteStudent([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var todoItem = await _context.Students.SingleOrDefaultAsync(m => m.ID == id);
            if (todoItem == null)
            {
                return NotFound();
            }

            _context.Students.Remove(todoItem);
            await _context.SaveChangesAsync();

            return Ok(todoItem);
        }

        
    }
}
