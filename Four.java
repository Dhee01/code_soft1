import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion
{
    private String question;
    private List<String> options;
    private int correctOption;

    public QuizQuestion(String question, List<String> options, int correctOption)
    {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion()
    {
        return question;
    }

    public List<String> getOptions()
    {
        return options;
    }

    public int getCorrectOption()
    {
        return correctOption;
    }
}
class Quiz 
{
    private List<QuizQuestion> questions;
    private int currentQuestionIndex;
    private int userScore;
    private Timer timer;
    private boolean isAnswered;

    public Quiz(List<QuizQuestion> questions)
    {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.userScore = 0;
        this .timer = new Timer();
    }

    public void startQuiz()
    {
        if (currentQuestionIndex < questions.size())
        {
            displayQuestion();
        }
        else
        {
            endQuiz();
        }
    }

    private void displayQuestion()
    {
        QuizQuestion currentQuestion = questions.get(currentQuestionIndex);

        System.out.println(currentQuestion.getQuestion());
        List<String> options = currentQuestion.getOptions();
        for (int i = 0; i < options.size(); i++)
        {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        
        startTimer();

        Scanner Scanner = new Scanner (System.in);
        System.out.print("Your choice: ");
        int userChoice = Scanner.nextInt();

        stopTimer(); // stop the timer when the user submits an answer
        checkAnswer(userChoice);
    }
    private void startTimer()
    {
        isAnswered = false;
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if (!isAnswered)
                {
                    System.out.println("Time's up! Moving to the next question.");
                    stopTimer();
                    nextQuestion();
                }
            }
        }, 15000); // 15 second timer 
    }

    private void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }

    private void checkAnswer(int userChoice)
    {
        QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
        if (userChoice == currentQuestion.getCorrectOption())
        {
            System.out.println("Correct! +1 point");
            userScore++;
        }
        else
        {
            System.out.println("Incorrect. The correct answer was: " + currentQuestion.getCorrectOption());
        }

        nextQuestion();
    }

    private void nextQuestion()
    {
        currentQuestionIndex++;
        startQuiz();
    }
    private void endQuiz()
    {
        System.out.println("Quiz finished! Your final score: " + userScore);
    }
}
class Main
{
    public static void main(String[]args)
    {
        // Creating sample quiz questions
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        quizQuestions.add(new QuizQuestion("What is the capital of France?", List.of("Berlin", "paris", "Madrid"), 2));
        quizQuestions.add(new QuizQuestion("What planet is known as the Red planet?", List.of("Mars", "Venus", "jupiter"), 1));

        // Starting the quiz
        Quiz quiz = new Quiz(quizQuestions);
        quiz.startQuiz();
    }
}