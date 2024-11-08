# CoursesAPI

Api flow

1.Login via OAUTH server
2.Roles

   USER -  can access  "/api/course"  - List all courses
   
   AUTHOR - can create course  /api/course/create 
   Initially  courses will be unapproved status.
   
   ADMIN  - can access "/api/course/unapproved" - List all unapproved courses
          - can approve a course.
