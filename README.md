1. application.yml 생성
2. 클래스 패키지 생성
3. index.html 생성 후 MainController 생성
4. Member 클래스들 생성
5. member_table 생성하는 MemberEntity 생성
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "member_id")
   private Long id;

   @Column
   private String memberEmail;

   @Column
   private String memberPassword;

   @Column
   private String memberName;

   @Column
   private String memberPhone;

   @Column
   private String fileName;

6. 업로드,수정 시간을 저장하기 위한 BaseEntity 클래스 생성
   @MappedSuperclass
   @EntityListeners(AuditingEntityListener.class)
   @Getter
   public abstract class BaseEntity {
   @CreationTimestamp
   @Column(updatable = false)
   private LocalDateTime createTime;

   @UpdateTimestamp
   @Column(insertable = false)
   private LocalDateTime updateTime;

7. save.html 생성 (controller에서 폼 이동 설계)
8. 회원가입 진행
9. index.html 에 로그인 a태그 작성
10. MemberLoginDTO 클래스 생성.
11. 로그인값을 세션에 넣어서 일치하면 저장
12. 로그아웃기능 작성
13. 게시글 작성을 위한 클래스 생성
14. BoardEntity 클래스를 만들고 코드작성.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column
    private String boardWriter;
    @Column
    private String boardTitle;
    @Column
    private String boardContents;
    @Column
    private int boardHits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

15. /board/save를 통해 게시글 DB 저장
16. 글 작성 시 파일 업로드 할 수 있는 코드를 BoardServiceImpl에 작성
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files" ;

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        MemberEntity memberEntity = mr.findByMemberEmail(boardSaveDTO.getBoardWriter());
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardSaveDTO, memberEntity);

        boardEntity.setFileName(fileName);
        boardEntity.setFilePath("/files/" + fileName);

        Long boardId = br.save(boardEntity).getId();
        return boardId;

17. 글 목록을 보기위한 findAll.html과 목록을 페이징해서 보기위한 코드 작성
    // 화면에 보여줄 글 갯수와 페이징 번호 선언
    public static final int PAGE_LIMIT = 5;
    public static final int BLOCK_LIMIT = 3;
    // 페이징처리된 글 목록.
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
        Page<BoardPagingDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/findAll";
    }

18. 글 제목을 누르면 상세정보를 볼 수 있는 findById.html 생성



