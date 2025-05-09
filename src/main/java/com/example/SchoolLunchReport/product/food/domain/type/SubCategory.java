package com.example.SchoolLunchReport.product.food.domain.type;

public enum SubCategory {
    //Rice 하위
    WHITE_RICE(Category.RICE), //흰쌀밥
    MIXED_GRAIN_RICE(Category.RICE), //잡곡밥
    FRIED_RICE(Category.RICE), //볶음
    SPECIAL_RICE(Category.RICE), //김밥, 주먹밥, 비빔밥 등
    // MAIN_DISH 하위
    MEAT(Category.MAIN_DISH), // 제육볶음, 불고기, 돈까스 등
    POULTRY(Category.MAIN_DISH), // 가금류:  닭갈비, 닭튀김 등
    FISH(Category.MAIN_DISH), // (생선류): 고등어조림, 생선구이 등
    FRIED(Category.MAIN_DISH), //  (튀김류): 치킨가라아게, 탕수육, 돈까스 등
    STEAMED_OR_BOILED(Category.MAIN_DISH), // (찜/조림류): 갈비찜, 코다리조림 등
    VEGETARIAN_MAIN(Category.MAIN_DISH), // (채식 메인): 두부스테이크, 야채볶음 등

    // SIDE_DISH 하위
    KIMCHI_VARIANT(Category.SIDE_DISH), //(김치류): 배추김치, 총각김치 등
    NAMUL(Category.SIDE_DISH),// (나물류): 시금치나물, 고사리 등
    PICKLED(Category.SIDE_DISH), // (장아찌류): 오이지, 깍두기 등
    EGG_BASED(Category.SIDE_DISH), // (계란류): 계란말이, 계란찜 등
    TOFU_OR_BEAN(Category.SIDE_DISH), // (두부/콩류): 두부조림, 콩자반 등
    SMALL_MEAT(Category.SIDE_DISH), // (소량 육류 반찬): 소고기장조림, 소세지볶음 등

    // SOUP 하위
    CLEAR_SOUP(Category.SOUP), //    맑은국: 미역국, 북어국 등
    SPICY_SOUP(Category.SOUP), //    얼큰한 국/찌개): 김치찌개, 육개장 등
    STEW(Category.SOUP), //    찌개류): 된장찌개, 순두부찌개
    MEAT_SOUP(Category.SOUP), //   (육류탕): 설렁탕, 갈비탕
    FISH_SOUP(Category.SOUP), //    어류탕): 대구탕, 매운탕
    // 디저트
    FRUIT(Category.DESSERT), //    (과일류): 바나나, 사과 등
    DAIRY(Category.DESSERT), //    (유제품): 요구르트, 푸딩
    BAKED(Category.DESSERT), //    (빵/케이크): 단팥빵, 카스테라
    SNACK(Category.DESSERT), // 완전 간식
    BEVERAGE(Category.DESSERT);//    음료): 쥬스, 식혜 등
    private final Category parentCategory;

    SubCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Category getParentCategory() {
        return parentCategory;
    }
}
