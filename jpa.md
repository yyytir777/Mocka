## JPA 필드 생성 전략

### @Id
- `@Id`만 있는 경우 : 생성 o
- `@Id`와 `@GeneratedValue`같이 둘 경우 : 생성 x


### @Column
- unique : 값이 유일해야함
- length : 최대 길이


### @Enumerated
- `@Enumerated(EnumType.ORDINAL)` : 해당 enum class의 element의 개수를 랜덤으로 return
- `@Enumerated(EnumType.STRING)` : 해당 enum class의 element를 랜덤으로 return


## @Temporal
- `@Temporal(TemporalType.DATE)` : java.sql.Date
- `@Temporal(TemporalType.TIME)` : java.sql.Time
- `@Temporal(TemporalType.TIMESTMAP)` : java.sql.TIMESTAMP


### @Lob
- String, char[], Character[] : CLOB
- byte[], Byte[], Serializable : BLOB

### @Transient
- 생성 X


### Embedded
- 해당 클래스도 생성

### 