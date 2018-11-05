on a customer’s birthday, we want to send a voucher to all long-term customers. How do we retrieve one that matches?

We have two predicates:

Birthday
Long-term customer — let us assume that the customer created an account at least two years ago.
Here’s how it would with the implementation using the JPA 2.0 Criteria API:

LocalDate today = new LocalDate();
CriteriaBuilder builder = em.getCriteriaBuilder();
CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
Root<Customer> root = query.from(Customer.class);
Predicate hasBirthday = builder.equal(root.get(Customer_.birthday), today);
Predicate isLongTermCustomer = builder.lessThan(root.get(Customer_.createdAt), today.minusYears(2); 
query.where(builder.and(hasBirthday, isLongTermCustomer));
em.createQuery(query.select(root)).getResultList();


he main problem with this code is that predicates are not easy to externalize and reuse because you need to set up the CriteriaBuilder, CriteriaQuery, and Root first. Also, code readability is poor because it is hard to quickly infer the intent of the code.


To be able to define reusable predicates, we introduced the specification interface
 It defines a specification as a predicate over an entity, which is exactly what our Specification interface represents. This actually only consists of a single method:

public interface Specification<T> {
  Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb);
}


When using Java 8, the code becomes very clear and easy to understand.

public CustomerSpecifications {
  public static Specification<Customer> customerHasBirthday() {
return (root, query, cb) ->{ 
return cb.equal(root.get(Customer_.birthday), today);
};
 }
 public static Specification<Customer> isLongTermCustomer() {
return (root, query, cb) ->{ 
        return cb.lessThan(root.get(Customer_.createdAt), new LocalDate.minusYears(2));
};
}
}




A client can now perform the following:

customerRepository.findAll(hasBirthday());
customerRepository.findAll(isLongTermCustomer());


d. We can combine these individual predicates to meet our business requirement. We have a helper class, specifications, that provides and(…)   and or(…) methods to concatenate atomic specifications.

customerRepository.findAll(where(customerHasBirthday()).and(isLongTermCustomer()));

public interface UserRepository 
  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {}
  
  
  https://www.baeldung.com/rest-api-search-language-spring-data-specifications
  
