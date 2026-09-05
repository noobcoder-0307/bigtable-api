from fastapi import FastAPI,HTTPException

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
