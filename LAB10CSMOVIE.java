//Birb
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LAB10CSMOVIE {
    private static ArrayList<CSMovie> movies = new ArrayList<>();

    public static void main(String[] args) {
        start();
        q1();
        q2();
        q3();
        q4();
        q5();
        q6();
        q7();
        q8();
        q9();
        q10();
        q11();
    }

    private static void start() {
        String row;
        File file = new File("movies.csv");

        try {
            Scanner sc = new Scanner(file);
            sc.nextLine(); // Skip header
            while (sc.hasNextLine()) {
                row = sc.nextLine();
                movies.add(new CSMovie(row));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
      private static String formatWithCommas(long number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(number);
    }
    private static double round2ThreeDecimals(double value) {
        double roundedValue = Math.round(value * 1000.0) / 1000.0;
        return roundedValue;
    }
    private static void q1() {
        double averageScore = movies.stream()
                                    .mapToDouble(movie -> {
                                        Double score = movie.getScore();
                                        return score != null ? score : 0.0;
                                    })
                                    .average()
                                    .orElse(0.0);
        double roundedoAvgScore = round2ThreeDecimals(averageScore);                            
        System.out.println("#\n Q1: Average score of movies:\n#\n" + "Average: "+ roundedoAvgScore);
    }

    private static void q2() {
        System.out.println("#\n Q2: Movies with votes > 1.9 m:\n");
        movies.stream()
              .filter(movie -> movie.getVotes() != null && movie.getVotes() > 1900000)
              .forEach(System.out::println);
              if(movies.stream().anyMatch(movie -> movie.getVotes() == null))
                  System.err.println("Nope! I can't find any.");
    
    }

    private static void q3() {
        System.out.println("#\n Q3: Movie with the highest gross:\n#");
        CSMovie maxGrossMovie = movies.stream()
                                      .filter(movie -> movie.getGross() != null)
                                      .max(Comparator.comparing(CSMovie::getGross))
                                      .orElse(null);
        if (maxGrossMovie != null) {
            String formatedGross = formatWithCommas(maxGrossMovie.getGross());
            System.out.println("Name: " + maxGrossMovie.getTitle() + " - Highest Gross: " + formatedGross);
            //System.out.println(maxGrossMovie); will print all []
        }
    }

    private static void q4() {
        System.out.println("#\n Q4: Genres available in the dataset:\n#");
        movies.stream()
              .map(CSMovie::getGenre)
              .filter(genre -> genre != null)
              .distinct()
              .forEach(System.out::println);
    }

    private static void q5() {
        System.out.println("#\n Q5: Movies with the shortest runtime (top 5):\n#");
        movies.stream()
              .filter(movie -> movie.getRuntime() != null)
              .sorted(Comparator.comparingInt(CSMovie::getRuntime))
              .limit(5)
              .map(movie -> String.format("%-55s ---> %s", movie.getTitle(), movie.getRuntime()))
              .forEach(System.out::println);
    }

    private static void q6() {
        System.out.println("#\n Q6: Movie with the highest and lowest budgets:\n#");
        CSMovie maxBudgetMovie = movies.stream()
                                       .filter(movie -> movie.getBudget() != null)
                                       .max(Comparator.comparing(CSMovie::getBudget))
                                       .orElse(null);
        CSMovie minBudgetMovie = movies.stream()
                                       .filter(movie -> movie.getBudget() != null)
                                       .min(Comparator.comparing(CSMovie::getBudget))
                                       .orElse(null);
        if (maxBudgetMovie != null && minBudgetMovie != null) {
            System.out.println("Movie with the highest budget: " + maxBudgetMovie.getTitle() + " - Budget: " + formatWithCommas(maxBudgetMovie.getBudget()));
            System.out.println("Movie with the lowest budget: " + minBudgetMovie.getTitle() + " - Budget: " + formatWithCommas(minBudgetMovie.getBudget()));
        }
    }

    private static void q7() {
        System.out.println("#\n Q7: Top 3 movies by genre:\n#");
        movies.stream()
              .filter(movie -> movie.getGenre() != null && movie.getScore() != null)
              .collect(Collectors.groupingBy(CSMovie::getGenre,
                                               Collectors.collectingAndThen(Collectors.toList(),
                                               list -> list.stream().sorted(Comparator.comparing(CSMovie::getScore).reversed()).limit(3))))
              .forEach((genre, topMovies) -> {
                  System.out.println("-== Genre: " + genre+ " ==-");
                  topMovies.limit(3).forEach(movie -> System.out.println(movie.getTitle() + " - Score: " + movie.getScore()));
              });
    }

    private static void q8() {
      
        System.out.println("#\n Q8: Top 3 action movies (descending by score, ascending by title if scores are equal):\n#");
        List<CSMovie> top3ActionMovies = movies.stream()
              .filter(movie -> "Action".equalsIgnoreCase(movie.getGenre()))
              .filter(movie -> movie.getScore() != null)
              .sorted(Comparator.comparing(CSMovie::getScore, Comparator.reverseOrder()).thenComparing(CSMovie::getTitle))
              .limit(3)
              .collect(Collectors.toList());
              
        for (CSMovie movie : top3ActionMovies) {
            System.out.println(movie.getTitle() + " - Score: " + movie.getScore());
        }
               //.forEach(movie -> System.out.println(movie.getTitle() + " - Score: " + movie.getScore()));
              // .forEach(System.out::println); --> WONT WORK
    }
    

    

    private static void q9() {
        System.out.println("#\n Q9: Total revenue per genre:\n#");
        Map<String, Long> revenuePerGenre = movies.stream()
                                                   .filter(movie -> movie.getGenre() != null && movie.getGross() != null)
                                                   .collect(Collectors.groupingBy(CSMovie::getGenre,
                                                                                  Collectors.summingLong(CSMovie::getGross)));
                                                                                  
        //revenuePerGenre.forEach((genre, totalRevenue) -> System.out.println(genre + ": " + totalRevenue));
        revenuePerGenre.forEach((genre, totalRevenue) -> {
            String formattedRevenue = formatWithCommas(totalRevenue);
            System.out.println(genre + ": " + formattedRevenue);
        });
    }

    private static void q10() {
        System.out.println("#\n Q10: Top 10 movie production companies with the most movies:\n#");
        movies.stream()
              .filter(movie -> movie.getCompany() != null)
              .collect(Collectors.groupingBy(CSMovie::getCompany, Collectors.counting()))
              .entrySet()
              .stream()
              .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
              .limit(10)
              .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    private static void q11() {
        System.out.println("#\n Q11: Movie with the most occurrences of the letter 'a' in its title:\n#");
        CSMovie mostOccurrencesMovie = movies.stream()
                                             .max(Comparator.comparingLong(movie -> {
                                                 String title = movie.getTitle();
                                                 return title != null ? title.chars().filter(c -> c == 'a').count() : 0;
                                             }))
                                             .orElse(null);
        if (mostOccurrencesMovie != null) {
            System.out.println(mostOccurrencesMovie.getTitle());
        }
    }
}
#   H o m e W o r k 4 S h a r e 
 
 