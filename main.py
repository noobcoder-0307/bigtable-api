from fastapi import FastAPI,HTTPException
from pydantic import BaseModel

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "Hello from your first API"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: str = None):
    return {"item_id": item_id, "q": q}

fake_items_db = {1: "Laptop", 2: "Keyboard", 3: "Monitor"}

@app.get("/products/{item_id}")
def read_product(item_id: int):
    if item_id not in fake_items_db:
        raise HTTPException(status_code=404, detail="Item not found")
    return {"item_id": item_id, "name": fake_items_db[item_id]}

class Item(BaseModel):
    name: str
    price: float
    in_stock: bool = True

@app.post("/items")
def create_item(item: Item):
    return {"received_item": item, "message": "Item created successfully"}

class ColumnFilter(BaseModel):
    column_name: str
    value: str

class BigtableQuery(BaseModel):
    row_key_prefix: str
    filters: list[ColumnFilter] = []
    limit: int = 10

@app.post("/query-preview")
def preview_query(query: BigtableQuery):
    return {"received_query": query}
