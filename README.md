# Spring Boot REST Template Project with Spring Security
This project uses Spring Boot to make HTTP requests via RestTemplate and has been updated to include Spring Security for enhanced authentication and authorization.

REST Template Configuration

Configure RestTemplate in a configuration class.
Inject RestTemplate into the service class for making requests to a 3rd-party URL and handling responses.
Operations include:
1. Get all records.
2. Get all records with a request parameter (single thread).
3. Get all records with a request parameter (using CompletableFuture).

Spring Security Addition (Updated on Mar 10)

1. Established a basic SecurityFilterChain.
2. Created roles and users along with RoleRepository and UserRepository for managing them.
3. Configured UserDetailsService to use userRepository for loading users by username and mapping roles to authorities.
4. Added an AuthController with:
  A @PostMapping("/register") endpoint for user registration.
  A @PostMapping("/login") endpoint for user authentication using AuthenticationManager. Successful authentication returns a JWT.
5. Integrated AuthenticationEntryPoint in the security configuration to handle authentication exceptions.
6. Implemented JwtGenerator for token generation, extracting username from JWT, and token validation.
7. Added JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter in the filter chain.
