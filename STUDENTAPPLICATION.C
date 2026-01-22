#include<stdio.h>
#include<conio.h>

struct Student
{
	int rno;
	char name[50];
	int std;
	int marks;
	int active;
	int rank;
};

#define SIZE 1000

struct Student srecords[SIZE];

int index=0,rnos=1;

void printStudent(struct Student s);

void assignRank();

void addStudent()
{
	struct Student s;

	if(index==SIZE)
	{
		printf("\nStudent List is Full.");
		return;
	}

	s.rno=rnos;
	flushall();
	printf("\nEnter Name: ");
	scanf("s.name");
	printf("\nEnter std: ");
	scanf("%d",&s.std);
	printf("\nEnter marks: ");
	scanf("%d",&s.marks);

	s.active = 1;

	srecords[index]=s;

	index++;
	rnos++;
	printf("\nStudent information is successfully iadded.");
}

void displayStudents()
{
	int i;
	if(index == 0)
	{
		printf("\nStudent record list is empty.");
		return;
	}
	for(i=0;i<index;i++)
	{
		if(srecords[i].active==1)
		{
			printStudent(srecords[i]);
		}
	}
}

void deleteStudent()
{
	int i,rno,flag=1;
	printf("\nEnter the roll no of student whose record you want to delete.");
	scanf("%d",&rno);

	for(i=0;i<index;i++)
	{
		if(srecords[i].rno==rno)
		{
			srecords[i].active=0;
			printf("\nStudent roll no %d is successfully deleted.",srecords[i]);
			flag=0;
			break;
		}
	}
	if(flag)
	{
		printf("\nStudent roll no %d is not found.",rnos);
	}
}

void searchStudentbyId()
{
	int i,rno,flag=1;
	printf("\nEnter the roll no you want to search student record.");
	scanf("%d",&rno);

	for(i=0;i<index;i++)
	{
		if(srecords[i].rno==rno)
		{
			printStudent(srecords[i]);
			flag=0;
			break;
		}
	}
	if(flag)
	{
		printf("\nStudent roll no %d is not found.",rnos);
	}
}

void updateStudentbyId()
{
	int i,rno,flag=1;
	printf("\nEnter the rno of student to update the record.");
	scanf("%d",&rno);

	for(i=0;i<index;i++)
	{
		if(srecords[i].rno == rno)
		{
			flushall();
			printf("\nEnter Name:");
			gets(srecords[i].name);
			printf("\nEnter std:");
			scanf("%d",&srecords[i].marks);
			printf("\nEnter Marks:");
			scanf("%d",&srecords[i].marks);

			flag=0;
			printf("\nStudent record with %d rno is updated successfully.",srecords[i]);
			break;
		}
	}
	if(flag)
	{
		printf("\nStudent %d rno is not found.",rno);
	}
}

void sortStudentbyMarks()
{
	struct Student temp;
	int i,j;

	if(index == 0)
	{
		printf("\nStudent record list is empty.");
		return;
	}
	for(i=0;i<index;i++)
	{
		for(j=(i+1);j<index;j++)
		{
			if(srecords[i].marks < srecords[j].marks)
			{
				temp=srecords[i];
				srecords[i]=srecords[j];
				srecords[j]=temp;
			}
		}
	}
	assignRank();
	printf("\nStudent records are sorted and ranks are assigned by marks successfully.");
	displayStudents();
}

void printStudent(struct Student s)
{
	printf("\n%d\t%s\t%d\t%d\tRank:%d",s.rno,s.name,s.std,s.marks,s.rank);
}

void assignRank()
{
	int i;

	if(index == 0)
	{
		return;
	}
	srecords[0].rank=1;

	for(i=1;i<index;i++)
	{
		if(srecords[i].marks == srecords[i-1].marks)
		{
			srecords[i].rank = srecords[i=1].marks;
		}
		else
		{
			srecords[i].rank = i+1;
		}
	}
}

void main()
{
	int choice;
	clrscr();

	do
	{
		printf("Enter the choice:");
		printf("\n1.Add Student");
		printf("\n2.Update Student By Id");
		printf("\n3.Delete Student By Id");
		printf("\n4.Display Student Information");
		printf("\n5.Search Student By Id");
		printf("\n6.Sort Student By Marks");
		printf("\n7.Exit The Application");
		scanf("%d",&choice);

		switch(choice)
		{
			case 1:addStudent();
			break;

			case 2:updateStudentbyId();
			break;

			case 3:deleteStudent();
			break;

			case 4:displayStudents();
			break;

			case 5:searchStudentbyId();
			break;

			case 6:sortStudentbyMarks();
			break;

			case 7:printf("\nStudent Application is Exited.");
				delay(500);
				exit(1);
			break;
		}
		}
		while(choice!=7);

		getch();
}